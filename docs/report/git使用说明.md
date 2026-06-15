# Git 使用说明

这份文档是给自己复习 Git 用的，前半部分按小白教程写，后半部分补充企业真实协作规范。

本文结合当前课程设计项目：

```text
项目名称：pl0-course-design
本地目录：E:\heart-dev\workspace\pl0-course-design
远程仓库：https://github.com/pengy-course-design/pl0-course-design.git
开发分支：hanlian/parser-semantic-skeleton
主分支：main
```

## 1. Git 和 GitHub 是什么

Git 是本地版本管理工具，可以理解成“代码存档系统”。

GitHub 是远程代码仓库，可以理解成“云端仓库”和“团队协作平台”。

关系如下：

```text
本地写代码
  ↓ git add
放入暂存区
  ↓ git commit
保存成本地版本
  ↓ git push
上传到 GitHub
```

一句话理解：

```text
Git 管代码版本。
GitHub 保存远程仓库，方便多人协作。
```

## 2. Git 的三个区域

Git 主要有三个区域：

```text
工作区：你正在编辑的真实文件
暂存区：下一次 commit 准备提交的文件清单
本地仓库：已经 commit 保存下来的历史版本
```

对应命令：

```text
工作区 → 暂存区：git add
暂存区 → 本地仓库：git commit
本地仓库 → GitHub：git push
GitHub → 本地：git pull
```

最常用流程：

```powershell
git status
git add 指定文件
git commit -m "提交说明"
git push
```

## 3. 当前项目仓库

课程设计仓库放在自己创建的 GitHub 组织下：

```text
组织：pengy-course-design
仓库：pl0-course-design
地址：https://github.com/pengy-course-design/pl0-course-design
```

本地项目目录：

```text
E:\heart-dev\workspace\pl0-course-design
```

确认当前项目会推送到哪里：

```powershell
git remote -v
```

正确结果：

```text
origin  https://github.com/pengy-course-design/pl0-course-design.git (fetch)
origin  https://github.com/pengy-course-design/pl0-course-design.git (push)
```

看到这个就说明当前项目只会推送到课程设计仓库，不会提交到别的组织或项目。

## 4. clone 仓库

进入工作区：

```powershell
cd E:\heart-dev\workspace
```

克隆仓库：

```powershell
git clone https://github.com/pengy-course-design/pl0-course-design.git
```

进入项目：

```powershell
cd pl0-course-design
```

这一步做了什么：

```text
把 GitHub 上的远程仓库下载到本地。
本地会生成 pl0-course-design 文件夹。
这个文件夹里面自带 Git 版本管理信息。
```

## 5. 最重要的命令：git status

查看当前状态：

```powershell
git status
```

它会告诉你：

```text
当前在哪个分支
本地和远程是否同步
哪些文件新增了
哪些文件修改了
哪些文件已经进入暂存区
是否还有内容没提交
```

干净状态：

```text
On branch main
Your branch is up to date with 'origin/main'.
nothing to commit, working tree clean
```

意思是：

```text
当前在 main 分支。
本地和 GitHub 已同步。
没有未提交的内容。
```

小白口诀：

```text
不知道现在发生了什么，就先 git status。
```

## 6. add、commit、push

新建或修改文件后，先看状态：

```powershell
git status
```

如果看到：

```text
Untracked files
```

意思是：

```text
Git 发现了新文件，但还没有正式管理它。
```

加入暂存区：

```powershell
git add 文件名
```

或者添加某个目录：

```powershell
git add src\main\java\com\pengy\pl0\lexer
```

添加当前目录下所有变化：

```powershell
git add .
```

提交：

```powershell
git commit -m "feat(lexer): add token model"
```

推送：

```powershell
git push
```

第一次推送新分支时：

```powershell
git push -u origin 分支名
```

`-u` 的作用：

```text
建立本地分支和远程分支的默认关联。
以后在这个分支上可以直接 git push。
```

## 7. commit 信息怎么写

推荐格式：

```text
<type>(<scope>): <summary>
```

常见 type：

```text
feat：新功能
fix：修复问题
docs：文档
test：测试
chore：初始化、配置、工程杂项
refactor：重构
```

本项目出现过的提交：

```text
chore: initialize project skeleton
chore: add Maven Spring Boot configuration
feat(lexer): add token model
feat(lexer): implement basic scanner
feat(parser): add AST node model
feat(parser): implement basic parser
feat(semantic): add symbol table and semantic checks
feat(ir): generate quadruples for PL0 statements
feat(web): add compile api
feat(web): add compiler demo interface
test: add PL0 sample programs
docs: add implementation notes and test cases
```

小白记忆：

```text
新增功能用 feat。
修 bug 用 fix。
写文档用 docs。
写测试用 test。
工程配置用 chore。
```

## 8. .gitignore 和 .gitkeep

`.gitignore` 用来告诉 Git 哪些文件不要提交。

本项目 `.gitignore` 里应该包含：

```gitignore
target/
*.class

.idea/
.vscode/
*.iml

.DS_Store
Thumbs.db

.env
.env.*
!.env.example

*.log
```

这些不应该提交：

```text
target/：Maven 编译产物
.idea/、.vscode/：编辑器配置
.env：环境变量和密码
*.log：日志文件
```

`.gitkeep` 是占位文件。

Git 默认不跟踪空目录，所以如果想让空目录出现在 GitHub 上，可以在里面放：

```text
.gitkeep
```

## 9. 分支是什么

分支可以理解成“另一条开发线”。

本项目使用过：

```text
main：最终稳定主分支
hanlian/parser-semantic-skeleton：开发分支
```

创建并切换分支：

```powershell
git checkout -b hanlian/parser-semantic-skeleton
```

查看分支：

```powershell
git --no-pager branch
```

示例：

```text
  hanlian/parser-semantic-skeleton
* main
```

星号 `*` 表示当前所在分支。

切换到 main：

```powershell
git checkout main
```

切回开发分支：

```powershell
git checkout hanlian/parser-semantic-skeleton
```

## 10. 本项目完整实战路线

这次课程设计真实走过的路线：

```text
1. 在 GitHub 创建组织 pengy-course-design
2. 在组织下创建仓库 pl0-course-design
3. clone 到本地 E:\heart-dev\workspace\pl0-course-design
4. 初始化 Maven + Spring Boot 项目骨架
5. 第一次 commit 并 push 到 main
6. 创建开发分支 hanlian/parser-semantic-skeleton
7. 在开发分支完成完整课程设计功能
8. 每个阶段单独 commit 和 push
9. 最后切回 main
10. merge 开发分支
11. 在 main 上 mvn test
12. push origin main
```

开发分支上完成的模块：

```text
Lexer 词法分析
AST 语法树模型
Parser 递归下降语法分析
Semantic 符号表和语义分析
IR 四元式生成
Web API
前端演示页面
测试样例 examples
说明文档 docs
```

最后合并到 main：

```powershell
git checkout main
git merge hanlian/parser-semantic-skeleton
mvn test
git push origin main
git status
```

最终状态：

```text
main 已同步 origin/main
11 个测试全部通过
工作区干净
```

## 11. 本项目常用命令速查

进入项目：

```powershell
cd E:\heart-dev\workspace\pl0-course-design
```

查看状态：

```powershell
git status
```

查看远程仓库：

```powershell
git remote -v
```

查看分支：

```powershell
git --no-pager branch
```

运行测试：

```powershell
mvn test
```

启动项目：

```powershell
mvn spring-boot:run "-Dspring-boot.run.arguments=--server.port=12020"
```

访问页面：

```text
http://localhost:12020
```

常规提交：

```powershell
git status
git add 指定文件
git commit -m "type(scope): summary"
git push
git status
```

合并开发分支到 main：

```powershell
git checkout main
git merge hanlian/parser-semantic-skeleton
mvn test
git push origin main
git status
```

## 12. IDEA 里提交 Git

IDEA 也可以提交 Git，真实企业里很多人都会用。

IDEA 和命令行的关系：

```text
IDEA 是图形界面。
底层还是 Git。
```

新建文件时，IDEA 弹窗：

```text
是否要安排以下文件添加到 Git？
```

意思等价于：

```powershell
git add 文件名
```

可以点：

```text
添加
```

但要注意：

```text
添加到 Git 不等于已经 commit。
```

IDEA 提交流程：

```text
1. 写代码
2. 保存文件
3. 运行 mvn test
4. 打开 Commit 窗口
5. 查看 Changed Files
6. 点每个文件看 Diff
7. 只勾选本次要提交的文件
8. 写 commit message
9. Commit
10. Push
```

IDEA 提交时最重要的是看 Diff：

```text
Diff 能看到你到底改了什么。
不要看到一堆文件就全部勾选。
```

不要提交：

```text
target/
.idea/
.env
*.log
临时文件
无关文件
```

建议：

```text
学习阶段多用命令行，理解 Git 原理。
熟悉后日常可以用 IDEA 提交。
遇到问题再回到命令行用 git status 排查。
```

## 13. 真实企业 Git 工作流

真实企业里通常不是所有人直接往 `main` 上提交。

常见分支：

```text
main / master：稳定主分支
dev / develop：日常集成分支，有些团队会用
个人功能分支：每个人开发自己的任务
```

常见流程：

```text
从 main 或 dev 拉出个人分支
  ↓
在个人分支写代码
  ↓
本地测试通过
  ↓
push 到远程个人分支
  ↓
发 Pull Request / Merge Request
  ↓
同事 Code Review
  ↓
CI 自动测试通过
  ↓
合并到 main 或 dev
```

例如：

```text
main
hanlian/parser-semantic
liangjiahao/lexer-ir
jingyilin/ui-test
```

为什么不直接推 main：

```text
main 要保持稳定。
多人直接推 main 容易互相影响。
代码没人检查容易把 bug 合进去。
自动测试没过会影响团队。
```

企业更看重：

```text
小步提交
提交信息清楚
测试通过
PR 描述清楚
Code Review 通过
不提交密码和无关文件
```

## 14. PR / MR 是什么

PR 是 Pull Request，GitHub 上的叫法。

MR 是 Merge Request，GitLab 上的叫法。

它们本质一样：

```text
请求把我的分支合并到目标分支。
```

比如：

```text
hanlian/parser-semantic → main
```

PR 里通常要写：

```text
改了什么
为什么改
怎么测试
有没有风险
截图或测试结果
```

PR 的作用：

```text
让别人看代码
让 CI 自动跑测试
避免坏代码直接进入 main
保留清楚的协作记录
```

课程设计可以简单一点，但真实企业基本都走 PR/MR。

## 15. pull 和 merge

拉取远程最新内容：

```powershell
git pull
```

意思是：

```text
把 GitHub 上的新提交下载到本地，并合并到当前分支。
```

合并分支：

```powershell
git merge 分支名
```

比如把开发分支合到 main：

```powershell
git checkout main
git merge hanlian/parser-semantic-skeleton
```

这次项目最后合并是 fast-forward。

fast-forward 的意思：

```text
main 没有新的分叉提交。
Git 只需要把 main 指针直接向前移动到开发分支最新提交。
所以没有产生额外 merge commit，也没有冲突。
```

## 16. 冲突是什么

冲突通常发生在：

```text
两个人修改了同一个文件的同一部分。
```

冲突文件里可能出现：

```text
<<<<<<< HEAD
当前分支内容
=======
要合并进来的内容
>>>>>>> branch-name
```

处理方法：

```text
1. 打开冲突文件
2. 手动保留正确内容
3. 删除 <<<<<<<、=======、>>>>>>> 标记
4. git add 冲突文件
5. git commit
```

冲突不是错误，只是 Git 不知道该保留谁，需要人来判断。

## 17. 撤销和反悔

文件改乱了，但还没有 add：

```powershell
git restore 文件名
```

意思是：

```text
放弃这个文件当前修改，恢复到最近一次提交。
```

文件已经 add，但还没 commit：

```powershell
git restore --staged 文件名
```

意思是：

```text
把文件从暂存区拿出来，但保留实际修改。
```

注意：

```text
git restore 文件名 会丢弃修改。
不确定时先 git status。
```

## 18. 查看历史

查看提交历史：

```powershell
git log
```

退出分页器：

```text
q
```

简洁历史：

```powershell
git log --oneline
```

查看最近 5 条：

```powershell
git --no-pager log --oneline -5
```

## 19. 常见问题升级版

### 19.1 warning: You appear to have cloned an empty repository.

含义：

```text
你克隆了一个空仓库。
```

处理：

```text
正常现象。
创建文件后 commit 并 push 即可。
```

### 19.2 Your branch is based on 'origin/main', but the upstream is gone.

含义：

```text
远程 main 还没有真正创建。
```

常见于空仓库第一次提交。

处理：

```powershell
git push -u origin main
```

### 19.3 push 失败：Connection was reset / Could not connect to server

含义：

```text
网络连接 GitHub 失败。
本地 commit 通常已经成功，只是没推上去。
```

判断：

```powershell
git status
```

如果看到：

```text
Your branch is ahead of 'origin/xxx' by 1 commit.
```

说明：

```text
本地比远程多 1 个提交，只需要重新 git push。
```

处理：

```powershell
git push
```

多试几次，确认网络正常即可。

### 19.4 LF will be replaced by CRLF

含义：

```text
Git 在 Windows 上处理换行符。
LF 是 Linux/macOS 常见换行。
CRLF 是 Windows 常见换行。
```

一般不影响提交。

### 19.5 IDEA 新建文件提示 Add to Git

含义：

```text
IDEA 发现你新建了文件，问你要不要让 Git 跟踪它。
```

等价于：

```powershell
git add 文件名
```

可以点添加，但后面仍然需要 commit。

### 19.6 文件已 staged 但又 modified

现象：

```text
Changes to be committed
Changes not staged for commit
```

原因：

```text
文件先 add 了，后来又被 IDEA 保存或格式化了一次。
暂存区里是旧版本，工作区里是新版本。
```

处理：

```powershell
git add 文件名
```

重新把最新版本加入暂存区。

### 19.7 target/ 出现在 git status

原因：

```text
Maven 编译生成了 target 目录。
```

处理：

在 `.gitignore` 中加入：

```gitignore
target/
```

### 19.8 端口被占用

报错：

```text
Identify and stop the process that's listening on port 8080
```

含义：

```text
Spring Boot 默认端口被别的程序占用了。
```

临时换端口：

```powershell
mvn spring-boot:run "-Dspring-boot.run.arguments=--server.port=12020"
```

访问：

```text
http://localhost:12020
```

### 19.9 git branch 卡住

如果出现分页器，按：

```text
q
```

以后可以用：

```powershell
git --no-pager branch
```

### 19.10 fast-forward merge 是什么

合并时看到：

```text
Fast-forward
```

意思是：

```text
目标分支没有新分叉。
Git 直接把目标分支指针移动到开发分支最新位置。
这是一种最简单、最干净的合并。
```

## 20. 今天这次项目我学会了什么

已经掌握：

```text
能创建 GitHub 组织和仓库
能 clone 仓库到本地
能查看 remote 地址
能用 git status 判断当前状态
能用 git add 加入暂存区
能用 git commit 保存本地版本
能用 git push 推送到 GitHub
能创建和切换分支
能在个人分支上开发
能处理空目录 .gitkeep
能配置 .gitignore 忽略 target
能看懂 staged / unstaged
能处理 push 网络失败后重试
能把开发分支合并到 main
能在 main 上跑 mvn test 验证
知道 IDEA 里 Add to Git 是什么
知道企业里一般通过 PR/MR 合并代码
知道 main 分支要保持稳定
```

## 21. 日常口诀

```text
先 status，看清楚。
改完代码，先测试。
只 add 该提交的文件。
commit 信息写明白。
push 前后都 status。
遇到报错先读提示。
不要乱删，不要乱 reset。
main 要稳定，功能走分支。
```

最常用命令组合：

```powershell
git status
mvn test
git add 指定文件
git commit -m "type(scope): summary"
git push
git status
```
