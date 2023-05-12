## Allure2报告中支持记录失败重试功能

### 应用场景
Allure2报告中支持记录失败重试功能的场景主要是针对测试用例执行失败后通过重试机制再次执行成功的情况。在这种场景下，Allure2报告可以记录每个用例的执行次数，以及在每次执行中是否成功或失败，并在报告中显示重试次数和成功/失败次数的趋势图。这可以帮助用户更好地了解测试用例的稳定性和可靠性，以及针对失败用例的重试机制是否有效。


#### 1. 提高测试用例的稳定性和可靠性

由于网络等外部因素的影响导致测试用例执行失败的场景，可以通过失败重试的机制来提高测试用例的稳定性和可靠性。

#### 2. 提高测试用例的成功率

由于测试用例设计不合理或者存在未知的Bug导致测试用例执行失败的场景，可以通过失败重试的机制来提高测试用例的成功率，并且便于及时发现和解决问题。

#### 3. 提高测试用例的执行成功率

由于测试用例的数据依赖关系，导致测试用例执行失败的场景，可以通过失败重试的机制来解决数据依赖关系的问题，提高测试用例的执行成功率。



总的来说，失败重试功能主要应用于提高测试用例的稳定性和可靠性，减少外部因素的影响，提高测试用例的成功率和执行效率。


### 失败重试方式
直接
```bash
 mvn clean test allure:report -Dsurefire.rerunFailingTestsCount=重试次数
```


- `rerunFailingTestsCount`是指在测试用例执行过程中，如果某个测试用例执行失败，则可以将其重试一定次数。这个参数定义了最大的重试次数。
  
  >如果设置为0，则不会进行重试。如果设置为1，则代表最多进行1次重试。以此类推。

>举个例子，如果一个测试用例执行失败，并且rerunFailingTestsCount被设置为3，那么该测试用例会被重新执行，总共执行4次（原来执行1次，再加上3次重试）。

这个功能主要用于解决由于测试环境不稳定等原因导致的偶发性失败的问题。通过多次重试，可以提高测试用例的稳定性，减少测试用例失败的概率。

#### 示例代码

```java
@DisplayName("失败重试验证")
public class RetryTest {
    @Test
    void reRunTest3() throws IOException {
        System.out.println("reRunTest3");
        throw new IOException("Exception in I/O operation");
    }

}
```


```bash
mvn clean test allure:report -Dsurefire.rerunFailingTestsCount=2
```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230316161301.png)

