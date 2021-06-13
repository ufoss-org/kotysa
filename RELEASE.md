## Release
* verify the current version you want to release in gradle.properties
* verify you are using SSH with GIT
* use openJDK 1.8 as project JDK
* do **publish** task
* Go to staging repos : https://s01.oss.sonatype.org/#stagingRepositories (ufoss account)
  * do **close** , refresh
  * then **release**
  * refresh again after several seconds, repo has gone -> it's released on maven central
* do **release** task (for minor release, press Enter for suggested versions : release version = current, new version = current + 1)
