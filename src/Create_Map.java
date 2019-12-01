import java.util.Arrays;
import java.util.Random;

public class Create_Map {
    private int[][] map;
    private int Max=1000;
    private int last_node;
//    long r1=System.currentTimeMillis();
//    Random random=new Random(r1);


    public void Map_Create(int node_num){
//        int random_next=random.nextInt()*(node_num-47)+1;            //生成编号1到48的随机数
        System.out.println("构建图.......、\n");

        map=new int[node_num][node_num];
        last_node=0;
        //初始化
        for (int i=0;i<node_num;i++){
            for (int j=0;j<node_num;j++){

                map[i][j]=Max;
            }
        }
        //保证一条源到目的的通路
        for (int i=0;i<node_num-1;i++){
            int random_weight=(int)(Math.random()*20+1);            //生成1到21的随机数
            map[i][i+1]=random_weight;
            map[i+1][i]=random_weight;
        }
        int times=0;    //迭代次数
        while (times<(node_num/4)){
//            int random_1=(int)(Math.random()*3+1);
//            int random_2=(int)(Math.random()*3+1);
            int random_1=(int)(Math.random()*(node_num-1));                    //
            int random_2=(int)(Math.random()*(node_num-1));
            int random_weight=(int)(Math.random()*20+1);            //生成1到21的随机数
            if(random_1!=random_2){
                map[random_1][random_2]=random_weight;
                map[random_2][random_1]=random_weight;
            }
            times++;
        }
        map[0][node_num-1]=Max;
        map[node_num-1][0]=Max;                 //不让起点终点直连
        System.out.println("构建完成.......、\n");

    }

    public int[][] getMap() {
        return map;
    }

    public void Show_Map(){
        System.out.println("构建的图为：\n"+Arrays.deepToString(map));
    }
}
