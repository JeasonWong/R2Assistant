package me.wangyuwei.r2assistant.plugin

import groovy.io.FileType
import org.gradle.api.Plugin
import org.gradle.api.Project

import java.util.regex.Matcher
import java.util.regex.Pattern

public class R2AssistantPlugin implements Plugin<Project> {

    final def FIELD_R_REGEX = '''@BindView\\([ ]*R2.id.([\\w]*)[ ]*\\)'''

    @Override
    void apply(Project project) {

        project.rootProject.task("sweepR2") {

            doLast {
                File srcDir = new File(project.projectDir.path.toString() + "/src/main/java")
                srcDir.eachFileRecurse(FileType.FILES) { File file ->
                    if (file.toString().endsWith(".java")) {
                        println file.toString()

                        String fileContent = new String(file.bytes)

                        Pattern p = Pattern.compile(FIELD_R_REGEX);
                        Matcher m = p.matcher(fileContent);
                        while (m.find()) {
                            println m.group(1) + "  >>>"
                        }

                    }
                }
            }

        }

    }
}
