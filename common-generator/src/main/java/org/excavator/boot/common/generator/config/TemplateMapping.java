/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.excavator.boot.common.generator.config;

/**
 * 模板映射
 * @author cmonkey
 */
public record TemplateMapping (
    String template,
    String dir,
    String suffix   ,
    String project,
    String packagePath,      // default calc from dir
    String ePadding,
    String sPadding
){
    public TemplateMapping{
        suffix = "java";
        ePadding = "";
        sPadding = "";
    }

    public String buildPackage(String project, String packageP, String modelName) {

        String localPackagePath = this.packagePath();

        if (this.packagePath() != null && !"".equals(this.packagePath())) {

            localPackagePath = localPackagePath.replaceAll("\\$\\{project\\}", project);
            localPackagePath = localPackagePath.replaceAll("\\$\\{packagePath\\}", packageP);
            localPackagePath = localPackagePath.replaceAll("\\$\\{model\\}", modelName);
            localPackagePath = localPackagePath.replaceAll("[\\/]", ".");
        }
        return localPackagePath;
    }

    public String buildDir(String project, String packageP) {
        String localDir = dir();

        if (this.dir() != null && !"".equals(this.dir())) {
            localDir = localDir.replaceAll("\\$\\{project\\}", project);
            localDir = localDir.replaceAll("\\$\\{packagePath\\}", packageP);
        }

        return localDir;
    }

    public String buildDir(String project, String packageP, String modelName) {
        String localDir = dir();

        if (this.dir() != null && !"".equals(this.dir())) {
            localDir = localDir.replaceAll("\\$\\{project\\}", project);
            packageP = packageP.replaceAll("[\\.]", "/");
            localDir = localDir.replaceAll("\\$\\{packagePath\\}", packageP);
            localDir = localDir.replaceAll("\\$\\{model\\}", modelName);

        }
        return localDir;
    }
}
