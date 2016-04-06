# Spring2016Team4

- [Team Trello Board](https://trello.com/b/cKEJLQlU/scheduling)
- Java Version: 1.8
- [Play Framework Documentation](https://www.playframework.com/documentation/2.4.x/Home)

## Developer Information
The newest version of play is not yet completely synchronized with IntelliJ IDEA, so it is a little finicky.
With that being said, Intellij is still the best IDE to use with this framework.  You MUST use the Ultimate edition.
Also be sure to install Intellij's Scala plugin when prompted.

The first time you open this project in intellij, select ' New project from existing sources'.  Select the project
name.  Import project from external mode = SBT.  On the last page, make sure 'Use auto-import' is checked
and nothing else is.  Do not download any sources or else you will get multiple copies of sources and have
problems.  Make sure you are using Java 1.8 for your SDK.  Click finish.
The next time you open the project in Intellij, you should be able to just open it normally.

There seems to be an issue with IntelliJ not recognizing JUnit and Play test classes.  The tests run fine
from the command line though.