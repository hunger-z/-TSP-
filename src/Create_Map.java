import java.util.Arrays;

public class Create_Map {
    private int[][] map;
    private double[] map_load;

//    long r1=System.currentTimeMillis();
//    Random random=new Random(r1);


    public void Map_Create(int node_num){

        System.out.println("构建图.......、\n");
        map=new int[node_num][node_num];
        //初始化
        int max = 999;
        for (int i = 0; i<node_num; i++){
            for (int j=0;j<node_num;j++){

                map[i][j]= max;
            }
        }
        //保证一条源到目的的通路
        for (int i=0;i<node_num-1;i++){
            int random_weight=(int)(Math.random()*20+1);            //生成1到21的随机数
            map[i][i+1]=random_weight;
            map[i+1][i]=random_weight;
        }
        int times=0;    //迭代次数
        while (times<22){

            int random_1=(int)(Math.random()*(node_num-1));                    //
            int random_2=(int)(Math.random()*(node_num-1));
            int random_weight=(int)(Math.random()*20+1);            //生成1到21的随机数
            if(random_1!=random_2){
                map[random_1][random_2]=random_weight;
                map[random_2][random_1]=random_weight;
            }
            times++;
        }
        map[0][node_num-1]= max;
        map[node_num-1][0]= max;                 //不让起点终点直连
        System.out.println("构建完成.......、\n");

    }
    public void ProductLoad(int node_count){
        map_load=new double[node_count];
        for (int i = 0; i<node_count; i++){
            map_load[i]=Math.random();                 //随机生成各节点的算力（0-1）

        }

    }

    public double[] getMap_load() {
        return map_load;
    }

    public int[][] getMap() {
        return map;
    }

    public void Show_Map(){
        System.out.println("构建的图为：\n"+Arrays.deepToString(map));
        System.out.println("各节点资源为：\n"+ Arrays.toString(map_load));
    }
}
