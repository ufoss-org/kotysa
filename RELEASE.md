## Release
* verify the current version you want to release in gradle.properties
* verify you are using SSH with GIT
* use openJDK 17 as project JDK
* do **kotysa publish mobile** task
* do **kotysa publish jpms** task
* Go to staging repos : https://s01.oss.sonatype.org/#stagingRepositories (ufoss account)
  * do **close** , refresh
  * then **release**
  * refresh again after several seconds, repo has gone -> it's released on maven central
* update sample projects with this just released Kotysa version, commit them
* Open and accept a pull-request on GitHub to target *master*, use rebase option
* do **release** task (for minor release, press Enter for suggested versions : release version = current,
new version = current + 1)
