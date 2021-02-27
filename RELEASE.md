## Release
* verify the current version is not already released in gradle.properties
* verify you are using SSH with GIT
* use openJDK 1.8 as project JDK
* do **publish** task
* login to bintray (ufoss account)
* verify artifacts are available on bintray (https://bintray.com/beta/#/ufoss/ufoss?tab=packages), Publish them
* do **release** task (for minor release, press Enter for suggested versions : release version = current, new version = current + 1)

## Merge
* git rebase master develop
* git checkout master
* git merge develop
* git push
