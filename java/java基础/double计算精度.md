public static void test() {
        Double prpdTotal = 98.15000000000;
        Double total = 0.0;

        List<Double> list = new ArrayList<Double>();
        list.add(45.040);
        list.add(11.590);
        list.add(21.590);
        list.add(14.020);
        list.add(5.910);
        for (int i = 0; i < list.size(); i++) {
            total += list.get(i);
        }

        System.out.println("原始 prpdTotal：" + prpdTotal);
        System.out.println("累加 total：" + total);
        BigDecimal b = new BigDecimal(total);
        Double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println("计算 f1：" + f1);
        System.out.println(f1.equals(prpdTotal));

    }