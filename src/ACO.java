import java.util.ArrayList;

import com.sin.java.plot.Plot;


public class ACO {
    Ant []ants;                    //蚂蚁
    private int ant_num;          //蚂蚁数
    public int [][]distance;     //节点间代价
    double [][]tao;             //信息素矩阵
    int node_num;              //节点数量
    public ArrayList<Integer> best_node_path;    //两点间的路径
    int node_path_bestlength;            //两点间的最优解的长度
    int[] node;                 //节点
    int current_node;           //当前节点
    private int[] length_array;   //每次迭代得到的最优长度的数组集合
    private int[] length_array2;   //每次迭代得到的最优长度的数组集合
    private double[] load;          //节点计算能力

    public void init(int[][] map,double[] map_load,int ant_count,int max_gen){
        ant_num=ant_count;
        ants=new Ant[ant_num];
        node=new int[map.length];
        node_num=map.length;
        current_node=0;
        distance=new int[node_num][node_num];
        load=new double[node_num];
        for (int i=0;i<map.length;i++){

            node[i]=i;
            //距离
            System.arraycopy(map[i], 0, distance[i], 0, map.length);
        }
        System.arraycopy(map_load, 0, load, 0, map_load.length);

        //初始化信息素矩阵
        tao=new double[node_num][node_num];
        for(int i=0;i<node_num;i++){
            for(int j=0;j<node_num;j++){
                tao[i][j]=0.1;
            }
        }

        best_node_path=new ArrayList<>();
        node_path_bestlength=Integer.MAX_VALUE;

        length_array=new int[max_gen];
        length_array2=new int[max_gen];
        //放蚂蚁
        for (int i=0;i<ant_count;i++){
            ants[i]=new Ant();
            ants[i].RandomSelect(node_num);
        }
    }

    //更新信息素矩阵
    public void UpdateTao(){
        double rou=0.5;   //挥发率
        //信息素挥发
        for(int i=0;i<node_num;i++){
            node[i]=i;
            for(int j=0;j<node_num;j++){
                tao[i][j]=tao[i][j]*(1-rou);
            }
        }
        //信息素更新，
        for(int i=0;i<ant_num;i++){

            for (int j=0;j<ants[i].node_path.size()-1;j++){
                tao[ants[i].node_path.get(j)][ants[i].node_path.get(j+1)]+=1.0/ants[i].node_path_length;
            }
        }
    }
    //maxgen ACO的最多循环次数
    //s_node 源点编号
    //d_node 目的点编号

    public void run(int maxgen){

        for (int runtime=0;runtime<maxgen;runtime++){
            //每次迭代，所有蚂蚁都要跟新一遍，走一遍
            for (int i=0;i<ant_num;i++){

                current_node=0;
                while (current_node!=node_num-1){

                    ants[i].SelectNode(tao,distance,current_node,load);
                    current_node=ants[i].getCurrent_node();
                }
                ants[i].Cal_pathLength(distance);  //计算蚂蚁获得的路径长度

                if(ants[i].node_path_length<node_path_bestlength){
                    //保留最优路径
                    node_path_bestlength=ants[i].node_path_length;

                    System.out.println("第"+runtime+"代(次迭代)，发现新的最优路径长度："+node_path_bestlength);
                    //更新路径
                    best_node_path.clear();     //如果某一个蚂蚁的路径长度比已记录的还小，则清空原有的并把该蚂蚁的路径加入
                    best_node_path.addAll(ants[i].node_path);
                }

            }
            length_array[runtime]=ants[0].node_path_length;   //哪一个无所谓，一次迭代下蚂蚁的都一样
            length_array2[runtime]=ants[ant_num-1].node_path_length;   //哪一个无所谓，一次迭代下蚂蚁的都一样
            //更新信息素
            UpdateTao();
            //重新设置蚂蚁到起点
            for(int i=0;i<ant_num;i++){
                ants[i].RandomSelect(node_num);
            }
        }
    }
    public void ReportResult(){
        System.out.println("最优路径长度是"+node_path_bestlength);
        System.out.println("蚁群算法最优路径输出：");
        System.out.print(best_node_path +">>\n");//输出最优路径
        System.out.println("蚁群算法第一只蚂蚁每一次迭代路径长度输出：\n");
        for (int value : length_array) {
            System.out.print(value+",");//输出所有路径长度
        }
        System.out.println("\n");
        System.out.println("第一只蚂蚁处理后：\n");
        for (int value : length_array) {
            System.out.print(Math.log(value)+",");//输出以e为底对数处理后的所有路径长度
        }
        System.out.println("\n");
        System.out.println("蚁群算法最后一只蚂蚁每一次迭代路径长度输出：\n");
        for (int value : length_array2) {
            System.out.print(value+",");//输出所有路径长度
        }
        System.out.println("\n");
        System.out.println("最后一只蚂蚁处理后：\n");
        for (int value : length_array2) {
            System.out.print(Math.log(value)+",");//输出以e为底对数处理后的所有路径长度
        }
        System.out.println("\n");


        Plot.figrue();
        Plot.hold_on();
        double[] y1=new double[length_array2.length];
        for(int i=0;i<length_array2.length;i++){
            y1[i]=length_array2[i];
        }
        Plot.plot(y1,"y1");
    }

}
