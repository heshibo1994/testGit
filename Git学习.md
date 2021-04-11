# Git学习

## 版本控制 

两个人对同一个类进行操作，如果没有文件管理，向同一个文件就会被覆盖。使用版本管理可以内容互备。

* 协同修改

  多个人并行的修改服务器的同一个文件

* 数据备份

  可以保存每一个目录和文件的当前状态，也可以保存提交过的历史状态

* 版本管理

  Git对文件进行快照

* 权限控制

  对团队中参与开发的如人员进行权限控制

  可以对团队外的人的代码进行审核

* 历史记录

  可以查看修改人，时间，内容，日志

* 分支管理

  ​	允许团队在开发过程中多条生产线进行任务，提高效率

​         git把数据看作是一个小型文件系统的一组快照，每次提交更新时，都会对当前的全部文件制作一个快照并保存快照的索引，重复的文件就不会创建索引，二十指向之前存储的文件。Git的工作方式为快照流。

## Git分布式管理

* 在本地有本地库，大部分操作在本地完成，不需要联网
* 完整性保证，哈希算法保证
* 尽可能添加数据，而不是删除修改数据，操作可逆
* 分支操作快捷流畅

## Git结构

本地库：

​		工作区：开发的地方

​		暂存区：临时存储的地方

​		本地库：历史版本

![image-20210403182254443](C:\Users\hsb\Desktop\git\image\本地库操作流.png)

远程库：

![image-20210403182254443](C:\Users\hsb\Desktop\git\image\远程库.png)

步骤：

PM创建远程库，通过pull将本地库内容发送到远程库。

开发通过clone将远程库的内容克隆到本地，同时初始化，在修改后通过push到远程库，PM可以pull拉去到本地库。

对于团队外的开发人员，通过fork将远程库复制一个新的远程库，修改完后发起pull request，通过审核后，原来的PM进行merge到本远程库。

## git命令行操作[本地]

### 1 初始化 git init 

```git
git init
```

.git 目录 存放本地库相关的子目录和文件，尽量不删除和修改

![image-20210403182254443](C:\Users\hsb\Desktop\git\image\初始化.png)

### 2 查看工作区和暂存区的状态 git status

```linux
git status

$ git status
On branch master

No commits yet

nothing to commit (create/copy files and use "git add" to track)

```

新建文件后  good.txt

```shell
$ git status
On branch master

No commits yet

Untracked files:
  (use "git add <file>..." to include in what will be committed)

        good.txt

nothing added to commit but untracked files present (use "git add" to track)
```

出现的没有被git管理，未被追踪的文件

### 3 将文件提交到暂存区 git add 

```shell

hsb@DESKTOP-2LVL707 MINGW64 /d/git (master)
$ git add good.txt

hsb@DESKTOP-2LVL707 MINGW64 /d/git (master)
$ git status
On branch master

No commits yet

Changes to be committed:
  (use "git rm --cached <file>..." to unstage)

        new file:   good.txt
```

### 4 将文件从暂存区移除 git rm --cached

```shell
hsb@DESKTOP-2LVL707 MINGW64 /d/git (master)
$ git rm --cached good.txt
rm 'good.txt'

hsb@DESKTOP-2LVL707 MINGW64 /d/git (master)
$ git status
On branch master

No commits yet

Untracked files:
  (use "git add <file>..." to include in what will be committed)

        good.txt

nothing added to commit but untracked files present (use "git add" to track)
```

### 5 从暂存区到本地库 git commit

```shell
$ git commit good.txt
[master (root-commit) d89cb04] My first commit good.txt
 1 file changed, 4 insertions(+)
 create mode 100644 good.txt
```

输入git commit 后出现vim编辑器，输入commit的内容。

 ```shell
$git status
On branch master
nothing to commit, working tree clean
 ```

随后对文件及进行修改

```shell
$ git status
On branch master
Changes not staged for commit:
  (use "git add <file>..." to update what will be committed)
  (use "git checkout -- <file>..." to discard changes in working directory)

        modified:   good.txt

no changes added to commit (use "git add" and/or "git commit -a")
```

可以使用git add 更新到暂存

可以使用git checkout -- 撤销修改。

修改后进行再次提交

```shell
hsb@DESKTOP-2LVL707 MINGW64 /d/git (master)
$git add good.txt

hsb@DESKTOP-2LVL707 MINGW64 /d/git (master)
$ git commit  -m"第一次修改，添加了EEE....."  good.txt
[master 38be38c] 第一次修改，添加了EEEE.....
 1 file changed, 2 insertions(+), 1 deletion(-)
```

### 6 查看记录 git log

```shell
$git log
commit 38be38c2beaa3ff8fcecba90a532b5dc49ab8319 (HEAD -> master)
Author: heshibo1994 <csuheshibo@163.com>
Date:   Sat Apr 3 19:29:50 2021 +0800

    第一次修改，添加了EEEE.....

commit d89cb047abbe1093ab4e6233cfc84e92add2603b
Author: heshibo1994 <csuheshibo@163.com>
Date:   Sat Apr 3 19:03:53 2021 +0800

    My first commit good.txt

```

```shell
git log --pretty=online      只显示一行
```

```shell
git reflog        显示移动指针的前进后退步数
014b2f7 (HEAD -> master) HEAD@{0}: commit: 第二次修改，添加FFFFFFFFF
38be38c HEAD@{1}: commit: 第一次修改，添加了EEEE.....
d89cb04 HEAD@{2}: commit (initial): My first commit good.txt

```

### 7 版本前进后退 git reset hard

基于索引进行版本的回退

```shell
$git reset --hard d89cb04
HEAD is now at d89cb04 My first commit good.txt
```

再回到最新的版本

```shell
$git reset --hard 014b2f7
HEAD is now at 014b2f7 第二次修改，添加FFFFFFFFF
```

版本只往后退

```
$git reset --hard HEAD^
HEAD is now at 38be38c 第一次修改，添加了EEEE.....
```

git reset 参数对比

--soft：仅仅在本地库移动指针

--mixed：1.在本地库移动指针2.重置暂存区

--hard：1.在本地库移动指针2.重置暂存区3重置工作区，相当于三个区域同步发生变化

### 找回删除的文件

创建新的文件，提交到工作区，随后删除该文件，可以通过回退到上一版本的形式找到被删除的文件。

```shell
$ git reflog
a147829 (HEAD -> master) HEAD@{0}: commit: 已经删除了测试文件
14caa5d HEAD@{1}: commit: 测试删除
014b2f7 HEAD@{2}: reset: moving to 014b2f7
38be38c HEAD@{3}: reset: moving to HEAD^
014b2f7 HEAD@{4}: reset: moving to 014b2f7
d89cb04 HEAD@{5}: reset: moving to d89cb04
014b2f7 HEAD@{6}: commit: 第二次修改，添加FFFFFFFFF
38be38c HEAD@{7}: commit: 第一次修改，添加了EEEE.....
d89cb04 HEAD@{8}: commit (initial): My first commit good.txt

hsb@DESKTOP-2LVL707 MINGW64 /d/git (master)
$ git reset --hard 14caa5d
HEAD is now at 14caa5d 测试删除
```

### 修改对比 git diff

```shell
$ git diff good.txt
diff --git a/good.txt b/good.txt
index cf860db..371f30e 100644
--- a/good.txt
+++ b/good.txt
@@ -3,4 +3,5 @@ b
 c
 d
 EEEEEEEEEEEEEEE
+我在这里修改了一行
 FFFFFFFFFFFFFFF
\ No newline at end of file

```

默认对比只拿工作区的文件和暂存区的文件做对比

```shell
hsb@DESKTOP-2LVL707 MINGW64 /d/git (master)
$ git add good.txt

hsb@DESKTOP-2LVL707 MINGW64 /d/git (master)
$ git diff good.txt

```

也可以和本地库中的某一个版本进行比较

```
$ git diff HEAD^ good.txt
diff --git a/good.txt b/good.txt
index cf860db..371f30e 100644
--- a/good.txt
+++ b/good.txt
@@ -3,4 +3,5 @@ b
 c
 d
 EEEEEEEEEEEEEEE
+我在这里修改了一行
 FFFFFFFFFFFFFFF
\ No newline at end of file
```

### 分支管理git branch

同时并行开发推进多个功能，提高开发效率

分支的创建，查看，切换

```shell
$git branch hot-fix

hsb@DESKTOP-2LVL707 MINGW64 /d/git (master)
$ git branch
  hot-fix
* master

hsb@DESKTOP-2LVL707 MINGW64 /d/git (master)
$ git checkout hot-fix
Switched to branch 'hot-fix'
M       good.txt
```

在当前分支，对当前分支上的文件进行修改后，再次提交，查看

```shell
$ git branch -v
* hot-fix d2428b5 在hot-fix分支修改
  master  14caa5d 测试删除
```

hot-fix 分支已经领先master一个版本。

### 合并分支 git merge

首先，切换到被合并的分支上,

```shell
$ git merge hot-fix
Updating 14caa5d..d2428b5
Fast-forward
 good.txt | 4 +++-
 1 file changed, 3 insertions(+), 1 deletion(-)
```

当对同一块内容进行修改时，合并时可能产生冲突。

```shell
$ git merge hot-fix
Auto-merging good.txt
CONFLICT (content): Merge conflict in good.txt
Automatic merge failed; fix conflicts and then commit the result.
```

```
<<<<<<< HEAD
测试冲突-----mster分支
=======
测试冲突-----hot-fix分支
>>>>>>> hot-fix
```

上一段是当前分支的内容，下一段是其他分支的内容。

```
$git status
On branch master
You have unmerged paths.
  (fix conflicts and run "git commit")
  (use "git merge --abort" to abort the merge)

Unmerged paths:
  (use "git add <file>..." to mark resolution)

        both modified:   good.txt

no changes added to commit (use "git add" and/or "git commit -a")

```

修改好后，再次提交

```shell
hsb@DESKTOP-2LVL707 MINGW64 /d/git (master|MERGING)
$git add .

hsb@DESKTOP-2LVL707 MINGW64 /d/git (master|MERGING)
$ git commit -m"fix 冲突"
[master bc1621f] fix 冲突

hsb@DESKTOP-2LVL707 MINGW64 /d/git (master)

```

就不再显示merging的状态

## Github操作

### 1 创建远端别名

```shell
hsb@DESKTOP-2LVL707 MINGW64 /d/git (master)
$git remote -v

hsb@DESKTOP-2LVL707 MINGW64 /d/git (master)
$ git remote add origin https://github.com/heshibo1994/testGit.git

hsb@DESKTOP-2LVL707 MINGW64 /d/git (master)
$git remote -v
origin  https://github.com/heshibo1994/testGit.git (fetch)
origin  https://github.com/heshibo1994/testGit.git (push)
```

将origin代替https://github.com/heshibo1994/testGit.git

### 2 推送 git push

 ```
$ git push origin master

Enumerating objects: 5, done.
Counting objects: 100% (5/5), done.
Delta compression using up to 4 threads
Compressing objects: 100% (3/3), done.
Writing objects: 100% (3/3), 347 bytes | 347.00 KiB/s, done.
Total 3 (delta 1), reused 0 (delta 0)
remote: Resolving deltas: 100% (1/1), completed with 1 local object.
To https://github.com/heshibo1994/testGit.git
   bc1621f..7eb5176  master -> master

 ```

### 3对远程仓库的拉取git pull = git merge+git fetch

```shell
$ git fetch origin master
remote: Enumerating objects: 5, done.
remote: Counting objects: 100% (5/5), done.
remote: Compressing objects: 100% (2/2), done.
remote: Total 3 (delta 1), reused 3 (delta 1), pack-reused 0
Unpacking objects: 100% (3/3), done.
From https://github.com/heshibo1994/testGit
 * branch            master     -> FETCH_HEAD
   7eb5176..7ae52ec  master     -> origin/master
```

fetch只是将远端内容下载下来，并不会合并,随后再merge

```
$git merge origin master
Updating 7eb5176..7ae52ec
Fast-forward
 good.txt | 2 ++
 1 file changed, 2 insertions(+)
```

### 4协同开发

如果当前版本不是最新版本，就不允许推送。

```shell
$ git push origin master
 ! [rejected]        master -> master (fetch first)
error: failed to push some refs to 'https://github.com/heshibo1994/testGit.git'
hint: Updates were rejected because the remote contains work that you do
hint: not have locally. This is usually caused by another repository pushing
hint: to the same ref. You may want to first integrate the remote changes
hint: (e.g., 'git pull ...') before pushing again.
hint: See the 'Note about fast-forwards' in 'git push --help' for details.

```

必须先拿到远程的状态,有冲突就解决冲突。

```shell
$git pull
remote: Enumerating objects: 5, done.
remote: Counting objects: 100% (5/5), done.
remote: Compressing objects: 100% (2/2), done.
remote: Total 3 (delta 1), reused 3 (delta 1), pack-reused 0
Unpacking objects: 100% (3/3), done.
From https://github.com/heshibo1994/testGit
   7ae52ec..062b130  master     -> origin/master
Auto-merging good.txt
CONFLICT (content): Merge conflict in good.txt
Automatic merge failed; fix conflicts and then commit the result.

```

## idea使用git

