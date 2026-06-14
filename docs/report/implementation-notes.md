# PL0 语言扩充课程设计实现说明

## 1. 项目目标

本项目以 PL0 语言为基础，实现 PL0 语言扩充版编译器前端，包含词法分析、语法分析、语义分析、中间代码生成和 Web 展示界面。

## 2. 扩充内容

本项目实现了以下 PL0 扩充：

- 扩充赋值运算：`+=`、`-=`
- 增加自增、自减运算：`++`、`--`
- 扩充 `for` 语句：`for <变量> := <表达式> to <表达式> do <语句>`

## 3. 技术方案

- 开发语言：Java 21
- 构建工具：Maven
- Web 框架：Spring Boot
- 前端页面：HTML、CSS、JavaScript
- 分析方法：递归下降分析
- 中间代码形式：四元式

## 4. 模块划分

### 4.1 词法分析模块

对应包：`com.pengy.pl0.lexer`

主要类：

- `TokenType`：词法类型枚举
- `Token`：词法单元对象
- `Lexer`：词法分析器

输出内容：Token 序列。

### 4.2 语法分析模块

对应包：`com.pengy.pl0.parser`

主要类：

- `Parser`：递归下降语法分析器
- `ParserException`：语法错误异常
- `parser.ast`：AST 语法树节点

输出内容：AST 结构。

### 4.3 语义分析模块

对应包：`com.pengy.pl0.semantic`

主要类：

- `Symbol`：符号对象
- `SymbolTable`：符号表
- `SemanticAnalyzer`：语义分析器
- `SemanticError`：语义错误

实现检查：

- 重复声明检查
- 未声明变量检查
- 常量不能被赋值
- 常量不能执行自增、自减
- `for` 循环变量必须声明且不能是常量

### 4.4 中间代码生成模块

对应包：`com.pengy.pl0.ir`

主要类：

- `Quadruple`：四元式
- `IntermediateCodeGenerator`：中间代码生成器

输出形式：

```text
(operator, arg1, arg2, result)
```

示例：

```text
(:=, 1, _, x)
(+, x, 2, x)
(j>, i, 10, L2)
```

### 4.5 Web 展示模块

对应包：`com.pengy.pl0.web`

主要接口：

```text
POST /api/compile
```

前端页面：

```text
src/main/resources/static/index.html
```

页面展示：

- 源代码输入
- Token 序列
- AST 概要
- 符号表
- 语义错误
- 四元式

## 5. 运行方式

进入项目目录：

```powershell
cd E:\heart-dev\workspace\pl0-course-design
```

运行测试：

```powershell
mvn test
```

启动项目：

```powershell
mvn spring-boot:run "-Dspring-boot.run.arguments=--server.port=12020"
```

浏览器访问：

```text
http://localhost:12020
```

## 6. 小组分工

| 成员 | 分工 |
|---|---|
| 景懿琳 | 界面设计、测试 |
| 韩炼 | 语法分析、语义分析 |
| 梁佳豪 | 中间代码生成、词法分析 |

## 7. 当前完成情况

- 已完成 Maven + Spring Boot 项目骨架
- 已完成词法分析
- 已完成 AST 模型
- 已完成递归下降语法分析
- 已完成符号表和语义检查
- 已完成四元式生成
- 已完成 Web 演示页面
- 已补充正例和反例测试样例