package com.uwu.study.dp.behavioral.strategy;


import com.uwu.study.dp.behavioral.strategy.strategy.AddStrategy;
import com.uwu.study.dp.behavioral.strategy.strategy.MultiplyStrategy;
import com.uwu.study.dp.behavioral.strategy.strategy.Strategy;
import com.uwu.study.dp.behavioral.strategy.strategy.SubtractionStrategy;

/**
 * 指对象有某个行为，但是在不同的场景中，该行为有不同的实现算法
 *
 * 优点
 * 1、 策略模式提供了管理相关的算法族的办法。策略类的等级结构定义了一个算法或行为族。恰当使用继承可以把公共的代码转移到父类里面，从而避免重复的代码。
 * 2、 策略模式提供了可以替换继承关系的办法。继承可以处理多种算法或行为。
 * 3、 使用策略模式可以避免使用多重条件转移语句。
 * 缺点
 * 1、 客户端必须知道所有的策略类，并自行决定使用哪一个策略类。这就意味着客户端必须理解这些算法的区别，以便适时选择恰当的算法类。换言之，策略模式只适用于客户端知道所有的算法或行为的情况。
 * 2、 策略模式造成很多的策略类，每个具体策略类都会产生一个新类。有时候可以通过把依赖于环境的状态保存到客户端里面，而将策略类设计成可共享的，这样策略类实例可以被不同客户端使用。换言之，可以使用享元模式来减少对象的数量。
 *
 */
public class StrategyPatternTest {
    public static void main(String[] args) {
        Strategy add = new AddStrategy();
        Strategy sub = new SubtractionStrategy();
        Strategy mul = new MultiplyStrategy();

        OperationContext operationContext = new OperationContext(add);
        operationContext.Operation(2023,817);

        operationContext = new OperationContext(sub);
        operationContext.Operation(2023,817);

        operationContext = new OperationContext(mul);
        operationContext.Operation(2023,817);
    }
}
