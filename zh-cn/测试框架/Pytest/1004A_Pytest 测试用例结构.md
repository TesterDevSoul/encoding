# 文章名
## 本章要点
1. 要点一
1. 要点
1. 要点
1. **要点**


## 学习目标

1. 。


## 思考

## 测试用例构成


测试用例通常由三个主要部分构成：

### 用例名称（Test Case Name）

用例名称是对测试用例的简要描述，用于标识和区分不同的测试场景。

用例名称应具有描述性，清晰地表达被测试的功能或行为。

### 用例步骤（Test Case Steps）

用例步骤是测试用例的执行步骤，描述了测试过程中需要执行的操作或操作序列。

用例步骤应该按照逻辑顺序编写，确保测试过程的一致性和可重复性。

### 用例断言（Test Case Assertions）

用例断言是测试用例的最后一步，用于验证测试的预期结果是否与实际结果一致。

用例断言通常使用断言语句或断言函数来检查测试结果，确保测试通过或失败的准确性。



```
用例名称: 用户登录测试

用例步骤:
1. 打开登录页面
2. 输入用户名和密码
3. 点击登录按钮

用例断言:
- 确认登录成功后，跳转到用户首页
- 检查用户的登录状态为已登录
```

## 方法级别的示例

```python
# ----- 用例名称 -----
def test_XXX(self):
    # ----- 用例步骤 -----
    # 测试步骤1
    # 测试步骤2
    # ----- 用例断言 -----
    # 实际结果 对比 预期结果
    assert ActualResult == ExpectedResult
```

## 类级别的示例
```python
class TestXXX:
    def setup(self):
        # 资源准备
        pass

    def teardown(self):
        # 资源销毁
        pass

    # ----- 用例名称 -----
    def test_XXX(self):
        # ----- 用例步骤 -----
        # 测试步骤1
        # 测试步骤2
        # ----- 用例断言 -----
        # 实际结果 对比 预期结果
        assert ActualResult == ExpectedResult
```

## 总结
- 总结一
- 总结二
- 总结三
https://github.com/Wechat-ggGitHub/Awesome-GitHub-Repo

[项目演示地址](https://github.com/testeru-pro/junit5-demo/tree/main/junit5-basic)


# 学习反馈

1. SpringBoot项目的父工程为( )。

   - [x] A. `spring-boot-starter-parent`
   - [ ] B.`spring-boot-starter-web`
   - [ ] C. `spring-boot-starter-father`
   - [ ] D. `spring-boot-starter-super`


<style>
  strong {
    color: #ea6010;
    font-weight: bolder;
  }
  .reveal blockquote {
    font-style: unset;
  }
</style>


