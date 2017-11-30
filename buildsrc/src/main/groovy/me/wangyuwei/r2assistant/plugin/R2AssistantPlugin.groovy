package me.wangyuwei.r2assistant.plugin

import groovy.io.FileType
import org.gradle.api.Plugin
import org.gradle.api.Project

import java.nio.charset.StandardCharsets
import java.util.regex.Matcher
import java.util.regex.Pattern

public class R2AssistantPlugin implements Plugin<Project> {

    final def FIELD_R_REGEX = '''@BindView\\([ |\\n]*R2\\.id\\.([\\w]*)[ ]*\\)'''
    final def FIELD_R2_REGEX = '''@IdRes[\\s]*public static final int ([\\w]*) = *[\\w]*;'''
    final def STR_CLASS_ID = '''public static final class id {'''

    @Override
    void apply(Project project) {

        project.rootProject.task("sweepR2") {

            doLast {
                File srcDir = new File(project.projectDir.path.toString() + "/src/main/java")
                srcDir.eachFileRecurse(FileType.FILES) { File file ->
                    if (file.toString().endsWith(".java")) {
                        println file.toString()

                        String fileContent = new String(file.bytes)

                        Pattern p = Pattern.compile(FIELD_R_REGEX)
                        Matcher m = p.matcher(fileContent)
                        while (m.find()) {
                            println m.group(1) + "  >>>"
                        }

                    }
                }

                println getPackageName(project.projectDir.path.toString() + "/src/main/AndroidManifest.xml")

                String packageName = getPackageName(project.projectDir.path.toString() + "/src/main/AndroidManifest.xml")

                File r2 = new File(project.buildDir.path.toString() + "/generated/source/r/debug/" + packageName.replaceAll("\\.", "/") + "/R2.java")

                String r2Content = new String(r2.bytes)

                Pattern p = Pattern.compile(FIELD_R2_REGEX)
                Matcher m = p.matcher(r2Content)
                while (m.find()) {
                    println m.group(1) + "  >>>"
                }

                int index = r2Content.indexOf(STR_CLASS_ID)
                StringBuilder sb = new StringBuilder()
                sb.append(r2Content.substring(0, index + STR_CLASS_ID.length()))
                sb.append('''\n\t@IdRes\n\tpublic static final int sub4 = 0x7f0a0064;\n''')
                sb.append(r2Content.substring(index + STR_CLASS_ID.length(), r2Content.length()))

                r2.delete()
                r2.withWriter(StandardCharsets.UTF_8.name()) { writer ->
                    writer.write(sb.toString())
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
