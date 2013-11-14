# Dlerss - File downloader from RSS
Note that this is a work in progress so the build is currently not working properly.

This project is inspired by RSSDler (https://code.google.com/p/rssdler/) but it does not have nearly as many features.
The only thing supported is downloading files linked in RSS (XML) feeds which matches some regular expression.

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
hotfeeds.regexTrue = "(KittyNews S\\d{2}E\d{2}|DogTimes Season [1-5])"
coolfeeds.regexFalse = "Fluffy"
hotfeeds.saveFolder = "/my/hot/stuff"
```

## Build
```bash
sbt package
```

## Run
```bash
scala Dlerss.jar
```

This will look for a configuration file in the current directory i.e. "application.conf". To specify another configuration:
```bash
scala Dlerss.jar folder/bad-ass.conf
```

Note that some paths like ../folder/application.conf is not supported yet. To avoid errors, just place application.conf in the same folder as the jar-file and start it from there until this is fixed.

### Dependencies
```bash
"com.typesafe" % "config" % "1.0.2"
```

## License
Copyright 2013 Anton Fagerberg (http://www.antonfagerberg.com).

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.