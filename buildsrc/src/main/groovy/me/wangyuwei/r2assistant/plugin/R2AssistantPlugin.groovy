package me.wangyuwei.r2assistant.plugin

import groovy.io.FileType
import org.gradle.api.Plugin
import org.gradle.api.Project

import java.nio.charset.StandardCharsets
import java.util.regex.Matcher
import java.util.regex.Pattern

public class R2AssistantPlugin implements Plugin<Project> {

    private final def FIELD_R_REGEX = '''@BindView\\([ |\\n]*R2\\.id\\.([\\w]*)[ ]*\\)'''
    private final def FIELD_R2_REGEX = '''@IdRes[\\s]*public static final int ([\\w]*) = *[\\w]*;'''
    private final def STR_CLASS_ID = '''public static final class id {'''

    @Override
    void apply(Project project) {

        project.rootProject.task("sweepR2") {

            doLast {
                project.rootProject.allprojects.each { Project subProject ->
                    if (subProject.plugins.hasPlugin("com.android.library")) {
                        R2Log.log("start scan ${subProject.name}")

                        Set<String> srcFieldsSet = new HashSet<>()
                        Set<String> r2FieldsSet = new HashSet<>()
                        Set<String> generateFieldsSet = new HashSet<>()

                        File srcDir = new File(subProject.projectDir.path.toString() + "/src/main/java")
                        srcDir.eachFileRecurse(FileType.FILES) { File file ->
                            if (file.toString().endsWith(".java")) {
                                String fileContent = new String(file.bytes)

                                Pattern p = Pattern.compile(FIELD_R_REGEX)
                                Matcher m = p.matcher(fileContent)
                                while (m.find()) {
                                    srcFieldsSet.add(m.group(1))
                                }
                            }
                        }

                        String packageName = getPackageName(subProject.projectDir.path.toString() + "/src/main/AndroidManifest.xml")

                        File r2File = new File(subProject.buildDir.path.toString() + "/generated/source/r/debug/" + packageName.replaceAll("\\.", "/") + "/R2.java")

                        String r2Content = new String(r2File.bytes)

                        Pattern p = Pattern.compile(FIELD_R2_REGEX)
                        Matcher m = p.matcher(r2Content)
                        while (m.find()) {
                            r2FieldsSet.add(m.group(1))
                        }

                        srcFieldsSet.each {
                            if (!r2FieldsSet.contains(it)) {
                                R2Log.log("add filed: ${it}")
                                generateFieldsSet.add(it)
                            }
                        }

                        int index = r2Content.indexOf(STR_CLASS_ID)
                        StringBuilder sb = new StringBuilder()
                        sb.append(r2Content.substring(0, index + STR_CLASS_ID.length()))

                        generateFieldsSet.each {
                            sb.append("\n\t@IdRes\n\tpublic static final int ${it} = 0x7f888888;\n")
                        }

                        sb.append(r2Content.substring(index + STR_CLASS_ID.length(), r2Content.length()))

                        r2File.delete()
                        r2File.withWriter(StandardCharsets.UTF_8.name()) { writer ->
                            writer.write(sb.toString())
                        }
                    }

                }
            }

        }

    }


    private String getPackageName(String manifestPath) {
        def file = new File(manifestPath)
        if (file.exists()) {
            def manifest = new XmlSlurper(false, false).parse(file)
            return manifest."@package".text()
        }
        return ""
    }

}
