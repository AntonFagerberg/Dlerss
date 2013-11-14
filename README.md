# Dlerss - File downloader from RSS
This project is inspired by RSSDler (https://code.google.com/p/rssdler/), but this project does not have as many features.
The only thing this application does is that it downloads files linked in RSS (XML) feeds if the titles are matched against some regular expression.

## Example configuration
```bash
# Names of all enabled feeds.
names = [ "coolfeeds", "hotfeeds" ]

# URL to feed (XML).
coolfeeds.url = "https://..."
# Scan intervals in minutes.
coolfeeds.scanTime = 10
# Download all files which has a match in:
coolfeeds.regexTrue = "(Kittens|Dogs)"
# But ingore all files which has a match in (set to ^$ to disable):
hotfeeds.regexFalse = "$^"
# Save files to this folder:
coolfeeds.saveFolder = "/my/cool/folder"

hotfeeds.url = "https://..."
hotfeeds.scanTime = 5
hotfeeds.regexTrue = "(KittyNews S\\d{2}E\\d{2}|DogTimes Season [1-5])"
coolfeeds.regexFalse = "Fluffy"
hotfeeds.saveFolder = "/my/hot/stuff"
```

## Build
To build a "fat" executable, use the SBT assembly provided in this project.

```bash
sbt assembly
```

This will generate a self-contained JAR-file, see command output for the location where it is saved.

## Run
If assembly has been used to create the JAR-file, you can execute the file with either Java or Scala:
```bash
scala dlerss.jar
```

```bash
java -jar dlerss.jar
```

By default, dlerss will look for a configuration file in the current directory called "application.conf". To specify another configuration file, simply specify it as the first argument:
```bash
scala Dlerss.jar ../folder/bad-ass.conf
```

### Dependencies
```bash
"com.typesafe" % "config" % "1.0.2"
```

## Regular Expressions
This is a good site to learn more: http://www.tutorialspoint.com/scala/scala_regular_expressions.htm
Note that to match for example digits, you have to use \\d with double back slashes to not mess up the string in the configuration file.

## License
Copyright 2013 Anton Fagerberg (http://www.antonfagerberg.com).

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.