---
notebook: JUnit5
title: Yaml文件解析
tags: JUnit5,yaml,jackson
---
# `jsckson-Yaml`
## `pom`文件导入对应相关依赖
```xml    
<properties>
  <jackson.version>2.13.1</jackson.version>
</properties>
<!--yaml文件解析-->
<dependency>
  <groupId>com.fasterxml.jackson.core</groupId>
  <artifactId>jackson-databind</artifactId>
  <version>${jackson.version}</version>
</dependency>
<dependency>
  <groupId>com.fasterxml.jackson.dataformat</groupId>
  <artifactId>jackson-dataformat-yaml</artifactId>
  <version>${jackson.version}</version>
</dependency>
<dependency>
  <groupId>com.fasterxml.jackson.datatype</groupId>
  <artifactId>jackson-datatype-jsr310</artifactId>
  <version>2.13.0</version>
</dependency>
```
## 解析`List`文件

### `orderOne.yaml`
```yaml
- item: No. 9 Sprockets
  quantity: 12
  unitPrice: 1.23
- item:  No. Widget (10mm)
  quantity: 10
  unitPrice: 3.45
```

#### 「1」直接声明对应文件类型
```java
public class YamlUnitTest {
    private static ObjectMapper mapper;

    @BeforeAll
    public static void setup() {
        mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        mapper.findAndRegisterModules();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @AfterAll
    public static void cleanup() {
        orderOutput.deleteOnExit();
    }
    @Test
    public void test1() throws IOException {
        TypeReference<List<HashMap<String, Object>>> typeReference = new TypeReference<List<HashMap<String, Object>>>() {
        };
        List<HashMap<String, Object>> hashMaps = mapper.readValue(new File("src/test/resources/yaml/orderOne.yaml"), typeReference);

        hashMaps.forEach(hashMap -> {
            System.out.println(hashMap);
            assertAll(
                    () -> assertThat(hashMap.get("item").toString(), startsWith("No")),
                    () -> assertThat(Integer.parseInt(hashMap.get("quantity").toString()), is(greaterThan(9))),
                    () -> assertThat(new BigDecimal(hashMap.get("unitPrice").toString()), is(closeTo(new BigDecimal(1.0),new BigDecimal(4.00))))
            );
        });
    }
}
```


#### 「2」实体类对应解析
##### 实体类
```java
package com.testeru.yaml;

import java.math.BigDecimal;

public class OrderLine {
    private String item;
    private int quantity;
    private BigDecimal unitPrice;
    
    public OrderLine() {
        
    }

    public OrderLine(String item, int quantity, BigDecimal unitPrice) {
        super();
        this.item = item;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderLine [item=" + item + ", quantity=" + quantity + ", unitPrice=" + unitPrice + "]";
    }
}

```
##### 测试方法
```java
package com.testeru.yaml;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class YamlUnitTest {
    private static ObjectMapper mapper;

    @BeforeAll
    public static void setup() {
        mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        mapper.findAndRegisterModules();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    }

    @AfterAll
    public static void cleanup() {
        orderOutput.deleteOnExit();
    }
  
    @Test
    public void test2() throws IOException {
        TypeReference<List<OrderLine>> typeReference = new TypeReference<List<OrderLine>>() {
        };
        List<OrderLine> orderLines = mapper.readValue(new File("src/test/resources/yaml/orderOne.yaml"), typeReference);
        orderLines.forEach(orderLine -> {
            System.out.println(orderLines);

            assertAll(
                    () -> assertThat(orderLine.getItem(), startsWith("No")),
                    () -> assertThat(orderLine.getQuantity(), is(greaterThan(9))),
                    () -> assertThat(orderLine.getUnitPrice(), is(closeTo(new BigDecimal(1.0),new BigDecimal(4.00))))
            );
        });
    }
}

```

### `orderSecond.yaml`
```yaml
orderLines:
  - item: No. 9 Sprockets
    quantity: 12
    unitPrice: 1.23
  - item: No. Widget (10mm)
    quantity: 10
    unitPrice: 3.45
```



#### 「1」直接声明对应文件类型
```java
package com.testeru.yaml;

public class YamlUnitTest {
    private static ObjectMapper mapper;

    @BeforeAll
    public static void setup() {
        mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        mapper.findAndRegisterModules();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @AfterAll
    public static void cleanup() {
        orderOutput.deleteOnExit();
    }

    @Test
    public void test4() throws IOException {

        TypeReference<HashMap<String,List<HashMap<String, Object>>>> typeReference = new TypeReference<HashMap<String,List<HashMap<String, Object>>>>() {
        };
        HashMap<String, List<HashMap<String, Object>>> stringListHashMap = mapper.readValue(new File("src/test/resources/yaml/orderSecond.yaml"), typeReference);

        stringListHashMap.get("orderLines").forEach(hashMap -> {
            System.out.println(hashMap);
            assertAll(
                    () -> assertThat(hashMap.get("item").toString(), startsWith("No")),
                    () -> assertThat(Integer.parseInt(hashMap.get("quantity").toString()), is(greaterThan(9))),
                    () -> assertThat(new BigDecimal(hashMap.get("unitPrice").toString()), is(closeTo(new BigDecimal(1.0),new BigDecimal(4.00))))
            );
        });
    }
   
}

```


#### 「2」实体类对应解析
##### 实体类
```java
package com.testeru.yaml;

import java.math.BigDecimal;

public class OrderLine {
    private String item;
    private int quantity;
    private BigDecimal unitPrice;
    
    public OrderLine() {
        
    }

    public OrderLine(String item, int quantity, BigDecimal unitPrice) {
        super();
        this.item = item;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderLine [item=" + item + ", quantity=" + quantity + ", unitPrice=" + unitPrice + "]";
    }
}

```

```java
package com.testeru.yaml;

import java.util.List;

/**
 * @program: xunit
 * @author: 盖盖
 * @description:
 * @Version 1.0
 * @create: 2021/12/24 3:12 下午
 */
public class One {
    private List<OrderLine> orderLines;

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    @Override
    public String toString() {
        return "Order [orderLines=" + orderLines + "]";
    }
}

```
##### 测试方法
```java
package com.testeru.yaml;

public class YamlUnitTest {
    private static ObjectMapper mapper;

    @BeforeAll
    public static void setup() {
        mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        mapper.findAndRegisterModules();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    }

    @AfterAll
    public static void cleanup() {
        orderOutput.deleteOnExit();
    }
      @Test
    public void test3() throws IOException {

        One one = mapper.readValue(new File("src/test/resources/yaml/orderSecond.yaml"), One.class);
        System.out.println(one.toString());

        one.getOrderLines().forEach(orderLine -> {
            System.out.println(orderLine);
            assertAll(
                    () -> assertThat(orderLine.getItem(), startsWith("No")),
                    () -> assertThat(orderLine.getQuantity(), is(greaterThan(9))),
                    () -> assertThat(orderLine.getUnitPrice(), is(closeTo(new BigDecimal(1.0),new BigDecimal(4.00))))
            );
        });
    }
}
```
## 解析`Map` + `List`文件
### `orderThree.yaml`
```yaml
orderNo: A001
date: 2019-04-17
customerName: Customer, Joe
orderLines:
  - item: No. 9 Sprockets
    quantity: 12
    unitPrice: 1.23
  - item: Widget (10mm)
    quantity: 4
    unitPrice: 3.45
```


#### 实体类对应解析
##### 实体类
```java
package com.testeru.yaml;

import java.math.BigDecimal;

public class OrderLine {
    private String item;
    private int quantity;
    private BigDecimal unitPrice;
    
    public OrderLine() {
        
    }

    public OrderLine(String item, int quantity, BigDecimal unitPrice) {
        super();
        this.item = item;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderLine [item=" + item + ", quantity=" + quantity + ", unitPrice=" + unitPrice + "]";
    }
}

```

```java
package com.testeru.yaml;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderNo;
    private LocalDate date;
    private String customerName;
    private List<OrderLine> orderLines;

    public Order() {

    }

    public Order(String orderNo, LocalDate date, String customerName, List<OrderLine> orderLines) {
        super();
        this.orderNo = orderNo;
        this.date = date;
        this.customerName = customerName;
        this.orderLines = orderLines;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<OrderLine> getOrderLines() {
        if (orderLines == null) {
            orderLines = new ArrayList<>();
        }
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        if (orderLines == null) {
            orderLines = new ArrayList<>();
        }
        this.orderLines = orderLines;
    }

    @Override
    public String toString() {
        return "Order [orderNo=" + orderNo + ", date=" + date + ", customerName=" + customerName + ", orderLines=" + orderLines + "]";
    }

}
```
##### 测试方法
```java
package com.testeru.yaml;

public class YamlUnitTest {
    private static ObjectMapper mapper;

    @BeforeAll
    public static void setup() {
        mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        mapper.findAndRegisterModules();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    }

    @AfterAll
    public static void cleanup() {
        orderOutput.deleteOnExit();
    }
        @Test
    public void test5() throws IOException {

        Order order = mapper.readValue(new File("src/test/resources/yaml/orderThree.yaml"), Order.class);
        System.out.println(order);
        assertAll(
                () -> assertEquals("A001", order.getOrderNo()),
                () -> assertEquals(LocalDate.parse("2019-04-17", DateTimeFormatter.ISO_DATE), order.getDate()),
                () -> assertEquals("Customer, Joe", order.getCustomerName()),
                () -> assertEquals(2, order.getOrderLines().size())
        );

    }
}
```