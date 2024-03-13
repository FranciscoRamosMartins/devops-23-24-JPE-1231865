# Class Assignment 1 Walkthrough: Version Control with GIT

## Introduction
This assignment falls under the DEVOPS curriculum within the Switch course, focusing on version control with Git. The following guide offers a detailed walkthrough of the assignment requirements, emphasizing practical steps and procedures.

## Summary
In the initial phase, we'll be addressing two features. Firstly, we'll add the 'jobYears' field, and secondly, within a branch named 'email-field,' we'll introduce an 'email' field. In the subsequent phase, conducted in a different branch titled 'fix-invalid-email,' we'll implement verification to ensure the validity of the email address. Each phase will be organized into smaller tasks, delimited by predefined version tags, and executed through command-line operations. Before proceeding with the assignment, two preliminary steps must be completed:
1. Establishing a local personal [repository](https://github.com/FranciscoRamosMartins/devops-23-24-JPE-1231865), configuring user credentials, and linking it to the remote repository.
```bash
git init
git config user.name "FranciscoRamosMartins"
git config user.email "1231865@isep.ipp.pt"
git remote add origin https://github.com/FranciscoRamosMartins/devops-23-24-JPE-1231865.git
```
2. Cloning the [Tutorial React.js and Spring Data Rest](https://github.com/spring-guides/tut-react-and-spring-data-rest) to the root of the repository.
```bash
git clone https://github.com/spring-guides/tut-react-and-spring-data-rest
```


## Part 1 - #ca1-part1
## v1.0.0
1. Begin by creating an Issue on GitHub titled "Copy the code of the Tutorial React.js and Spring Data REST Application into a new folder named CA1" to track the changes made in this part of the assignment.
2. Open your terminal at the repository's root and create a new folder named "CA1".
```bash
mkdir CA1
```
3. Remove the .git directory from the tutorial application and move its contents into the newly created "CA1" folder using the appropriate command.
```bash
rm -rf tut-react-and-spring-data-rest/.git
mv tut-react-and-spring-data-rest/* CA1
```

4. Remove the empty folder, commit the changes and link them to the corresponding GitHub issue.
```bash
rm -rf tut-react-and-spring-data-rest
git add .
git commit -m "[Initial] - Close #1 Initial commit with the
              tut-react-and-spring-data-rest application in the CA1 folder."
git push
```

5. Tag the version of the application as v1.0.0.
```bash
git tag -a v1.0.0
git push master v1.0.0
```

## v1.2.0
1. Create a new issue on GitHub titled "Add a new field to record the number of years of the employee in the company (e.g. jobYears) along with respective unit tests"
2. Add a new field named 'jobYears' to the Employee class and update all relevant methods accordingly.
3. Write unit tests for the new field in the EmployeeTest class.
4. Commit the changes and link them to the corresponding GitHub issue.
```bash
git add .
git commit -m "[Feat] - Close #2 Added jobYears field, along with respective
              unit tests for Employee class (Class EmployeTest), and updated
              DatabaseLoader class with an additional field."
git push
```
5. Tag the version of the application as v1.2.0.
```bash
git tag v1.2.0
git push master v1.2.0
```


## v1.3.0
1. Create a new issue on GitHub titled "Create a branch called email-field and add support for an email field."
2. Create and switch to a new branch named 'email-field'.
```bash
git checkout -b email-field
```
3. Add a new field named 'email' to the Employee class and update all relevant methods.
4. Commit the changes.
```bash
git add .
git commit -m "[Feat] - Close #3 Added support for an email field. Respective unit
              tests updated and additional field added in DatabaseLoader class."
```
5. Merge back into the master branch.
```bash
git checkout master
git merge email-field
```
6. Tag the version of the application as v1.3.0.
```bash
git tag v1.3.0
git push master v1.3.0
```

7. At the end of part 1, mark your repository with the tag ca1-part1.
```bash
git tag ca1-part1
git push master ca1-part1
```

## Part 2 - #ca1-part2
## v1.3.1
1. Create an Issue on GitHub titled "Create a branch called fix-invalid-email. The server should only accept Employees with a valid email (e.g., an email must have the ”@” sign)."
2. Create and switch to another branch named 'fix-invalid-email'.
```bash
git checkout -b fix-invalid-email
```
3. Implement email validation in the Employee class and accompany it with thorough testing.
4. Commit the changes.
```bash
git add .
git commit -m "[Fix] - Close #4 Added verification to check if e-mail contains the 
              '@' sign,respective test added."
```
5. Merge back into the master branch.
```bash
git checkout master
git merge fix-invalid-email
```
6. Tag the version of the application as v1.3.1.
```bash
git tag v1.3.1
git push master v1.3.1
```

7. At the end of part 2, mark your repository with the tag ca1-part2.
```bash
git tag ca1-part2
git push master ca1-part2
```

## Version Control Alternative: Mercurial

Mercurial, akin to Git, stands as a distributed version control system (DVCS) facilitating developers in tracking and overseeing alterations to their codebase. Nonetheless, distinctive characteristics and parallels exist between the two, shaping their utilization according to project requisites and team inclinations.

### Contrast with Git

1. **User-Friendliness**: Mercurial garners acclaim for its straightforwardness and direct command framework. Novices might perceive Mercurial as more accessible compared to Git, which demands a steeper learning curve due to its extensive array of features and commands.


2. **Branching and Integration**: While both Git and Mercurial endorse branching and integration, Git's framework offers greater adaptability. Git's branches are nimble, allowing for easy creation, merging, and deletion, thereby encouraging experimentation with novel features. Mercurial adopts a slightly altered approach, where branches maintain permanence, and clones are frequently employed for feature development.


3. **Performance**: Git typically outshines in performance for sizable projects owing to its efficient branch handling and compressed data configuration. Nonetheless, Mercurial offers satisfactory performance for most projects and may prove simpler for rudimentary operations.


4. **Tooling and Fusion**: With broader adoption, Git boasts a more extensive array of tools and integrations, encompassing renowned platforms like GitHub, GitLab, and Bitbucket. Although Mercurial also enjoys support from various tools, the ecosystem remains comparatively smaller.

### Application of Mercurial to Assignment Objectives

Command-line operations in Mercurial closely mirror those in Git, with analogous procedures for repository establishment, alteration propagation, version marking, and branching. The following commands illustrate the application of Mercurial to the CA1 assignment objectives:

1. **Establishing the Repository**:
    ```bash
    hg init
    hg add .
    hg commit -m "initial commit"
    ```

2. **Propagating Alterations**:
    ```bash
    hg push
    ```

3. **Marking Versions**:
    ```bash
    hg tag <tagname>
    hg push --tags
    ```

4. **Branching for Features and Fixes**:
    ```bash
    hg branch <branchname>
    hg merge
    hg commit
    ```

5. **Concluding with Tags**:
    ```bash
    hg tag ca1-part2
    hg push --tags
    ```

By adhering to these procedures, one can manage version control for the CA1 assignment employing Mercurial, furnishing an alternative to Git that might cater to varying project requisites or individual preferences.

## Final Remarks

The successful culmination of the CA1 assignment exemplified the adept utilization of Git in enhancing a React.js and Spring Data REST application through the introduction of new functionalities and the application of version control best practices. The exploration of Mercurial as an alternative version control mechanism provided invaluable insights into the diverse array of tools available to developers, accentuating the significance of selecting the appropriate tool contingent upon project requisites and team dynamics. The process underscored the pivotal role of version control in overseeing code alterations, ensuring code integrity, and facilitating collaborative development endeavors. Through this assignment, the pragmatic utilization of Git and the contemplation of Mercurial have reaffirmed the fundamental tenets of version control within software development practices.
