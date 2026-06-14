# PL0 语言扩充测试用例

## 1. 正例：扩充赋值与自增自减

文件：`examples/valid-extended-assignment.pl0`

输入：

```pl0
const a = 10;
var x;
begin
  x := 1;
  x += a;
  x -= 3;
  x++;
  x--;
end.
```

预期结果：

- 词法分析识别 `+=`、`-=`、`++`、`--`
- 语法分析通过
- 语义分析通过
- 生成赋值、自增、自减相关四元式

## 2. 正例：for 循环

文件：`examples/valid-for-loop.pl0`

输入：

```pl0
var i, sum;
begin
  sum := 0;
  for i := 1 to 5 do
    sum += i;
end.
```

预期结果：

- 词法分析识别 `for`、`to`、`do`
- 语法分析生成 for 语句节点
- 语义分析检查循环变量 `i` 已声明
- 生成 label、条件跳转、回跳相关四元式

## 3. 反例：未声明变量

文件：`examples/invalid-undeclared-variable.pl0`

输入：

```pl0
begin
  x += 1;
end.
```

预期结果：

- 词法分析通过
- 语法分析通过
- 语义分析报错：变量 `x` 未声明
- 不生成四元式

## 4. 反例：常量被赋值

文件：`examples/invalid-const-assignment.pl0`

输入：

```pl0
const a = 1;
begin
  a := 2;
end.
```

预期结果：

- 词法分析通过
- 语法分析通过
- 语义分析报错：常量 `a` 不能被赋值
- 不生成四元式

## 5. 自动化测试

项目中已编写 JUnit 测试，运行命令：

```powershell
mvn test
```

测试覆盖：

- Lexer 扩充符号识别
- Parser 扩充语法识别
- Semantic 语义错误检查
- IR 四元式生成