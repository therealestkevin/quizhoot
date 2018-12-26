# Contributing

## Pull/Push Process

1. `git pull --rebase`
2. If there are merge conflicts, `git status` to find out what
   * After fixing conflicts, `git add <changed files>` and then `git rebase --continue`
   * At any point, use `git rebase --abort` to start over
3. `git push`

[Helpful guide](https://www.atlassian.com/git/tutorials/comparing-workflows)

## Merging

1. `git checkout master`
2. `git pull --rebase origin master`
3. `git merge <branch>`
4. After committing conflict fixes, `git push`