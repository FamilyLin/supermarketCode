import person.Customer;
import supermarket.LittleSuperMarket;
import supermarket.Merchandise;

import java.util.Scanner;

public class RunLittleSupperMarketAppMain {
    public static void main(String[] args) {
        //创建一个小超市类
        LittleSuperMarket littleSuperMarket = new LittleSuperMarket();
        //依次给超市的名字，地址，停车位赋值
        littleSuperMarket.superMarketName = "兴家超市";
        littleSuperMarket.address = "西苑华庭888号";
        littleSuperMarket.parkingCount = 10;
        //给超市200种商品
        littleSuperMarket.merchandises = new Merchandise[200];
        //统计用的数组
        littleSuperMarket.merchandiseSold = new int[littleSuperMarket.merchandises.length];
        //为了使用方便，创建一个商品数组引用，和littleSupermarket。merchandises指向同一个数组对象
        Merchandise[] all = littleSuperMarket.merchandises;

        //遍历并给200种商品赋值
        for (int i = 0; i < all.length; i++) {
            //创建并给商品的属性赋值
            Merchandise m = new Merchandise();
            m.name = "商品" + i;
            m.count = 50;//m的个数
            m.purchasePrice = Math.random() * 200;
            m.soldPrice = m.purchasePrice * (1 + Math.random());
            m.id = "ID" + i;
            //用创建的商品，给商品数组的第i个引用赋值，all和小超市的商品数组引用指向的是同一个数组对象
            all[i] = m;
        }

        System.out.println("超市开始营业");

        boolean open = true;
        Scanner in = new Scanner(System.in);
        while (open) {
            System.out.println("My name is:" + littleSuperMarket.superMarketName);
            System.out.println("My address is:" + littleSuperMarket.address);
            System.out.println("the park count have:" + littleSuperMarket.parkingCount);
            System.out.println("the incomingSum is:" + littleSuperMarket.incomingSum);
            System.out.println("have the count of merchandises are:" + littleSuperMarket.merchandises.length + "种");
            Customer customer = new Customer();
            customer.name = "顾客编号为：" + ((int) (Math.random() * 100000)) + "的顾客";
            customer.money = (1 + Math.random()) * 1000;
            customer.isDrivingCar = Math.random() > 0.5;
            //---------开门迎客------10
            //开车来的分配车位，没有车位就不能进了
            if (customer.isDrivingCar) {
                if (littleSuperMarket.parkingCount > 0) {
                    System.out.println("欢迎" + customer.name + "驾车而来，本店为您安排了车位，车位编号为：" + littleSuperMarket.parkingCount);
                    littleSuperMarket.parkingCount -= 1;
                } else {
                    System.out.println("不好意思，本店车位已满");
                    continue;
                }
            } else {
                System.out.println("欢迎" + customer.name + "光临本店");
            }

            //接待此顾客
            double totalCost = 0;
            while (true) {
                System.out.println("本店提供" + littleSuperMarket.merchandises.length + "种商品，请输入您要购买的商品编号(如果不想购买请输入小于0的数）：");
                int merchandiseId = in.nextInt();
                if (merchandiseId < 0) {
                    System.out.println("您本次购买了" + totalCost + "元的商品，欢迎您下次光临");
                    break;
                }

                if (merchandiseId >= littleSuperMarket.merchandises.length) {
                    System.out.println("本店没有该商品，欢迎继续挑选");
                    continue;
                }

                //商品有，问顾客要购买多少
                Merchandise toBuy = littleSuperMarket.merchandises[merchandiseId];
                System.out.println(toBuy.name + "单价" + toBuy.soldPrice + "。请问要购买多少：");
                int numToBuy = in.nextInt();

                //不想买，看看也欢迎
                if (numToBuy <= 0) {
                    System.out.println("欢迎下次选购本商品");
                    continue;
                }else{
                    System.out.println("购买成功");
                }

                //买的太多，库存不够
                if (numToBuy > toBuy.count) {
                    System.out.println(toBuy.name + "只有" + toBuy.count + "件了，不够" + numToBuy + "欢迎继续选购");
                    continue;
                }

                //顾客钱不够
                if (numToBuy * toBuy.soldPrice + totalCost > customer.money) {
                    System.out.println("您的余额不足");
                    continue;
                }

                //钱也够，货也够，更新顾客此次消费的总额
                totalCost += numToBuy * toBuy.soldPrice;

                //更新商品库存
                toBuy.count -= numToBuy;
                //更新今日销售数据
                littleSuperMarket.merchandiseSold[merchandiseId] += numToBuy;
            }
            //归还车位
            if (customer.isDrivingCar) {
                littleSuperMarket.parkingCount++;
            }

            //更新数据
            customer.money -= totalCost;
            littleSuperMarket.incomingSum += totalCost;
            System.out.println(customer.name + "共消费" + totalCost + "欢迎继续选购");


            System.out.println("请问是否继续营业，如果是，请输入：true；如果否，请输入：false");
            open = in.nextBoolean();

        }
        System.out.println("今日停止营业");
        System.out.println("今日销售额为：" + littleSuperMarket.incomingSum + "营业统计如下：");
        double allNetIncoming = 0;
        double allIncoming = 0;
        for (int i = 0; i < littleSuperMarket.merchandiseSold.length; i++) {
            int sold = littleSuperMarket.merchandiseSold[i];
            if (sold > 0) {
                Merchandise m = littleSuperMarket.merchandises[i];
                double netIncoming = sold * (m.soldPrice - m.purchasePrice);
                double incoming = sold * m.soldPrice;
                System.out.println(littleSuperMarket.merchandises[i].name + "售出" + sold + "个。销售额为：" + incoming + "毛利润为：" + netIncoming);
                allNetIncoming +=netIncoming;
                allIncoming += incoming;
            }
        }
        System.out.println("总销售额为：" + allIncoming + "毛利润为：" + allNetIncoming);
    }
}
