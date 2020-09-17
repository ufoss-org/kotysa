## Release
* verify the current version is not already released in gradle.properties
* verify you are using SSH with GIT
* do **publish** task
* verify artifacts are available on bintray, Publish them
* do **release** task (for minor release, press Enter for suggested versions : release version = current, new version = current + 1)

## Merge
* git rebase master develop
* git checkout develop
* git merge develop
* git push
