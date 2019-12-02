
import java.util.ArrayList;


public class Ant {

    public ArrayList<Integer> node_path;            //路径，源点到目的节点的
    public int node_path_length;     //源点到目的节点的路径长度
    private int node_num;           //节点总数
    public int[] visited;   //取值是0或1，1表示访问过，0表示没访问过
    private int c_node;
    private double last_load;   //剩余需卸载的量



    //index，该选择第index个节点
    //随机选择下一节点，node_num为当前节点编号
    //tao   全局的信息素信息
    //distance  全局的距离矩阵信息
    //select_node 当前位置

    public void SelectNode(double[][]tao,int[][]distance,int select_node,double[] load){

        //计算选中概率所需系数
        double[] possibility;
        possibility=new double[node_num];
        double alpha=1.8;
        double beta=0.5;
        double sum=0.0;                  //预选所有路径的信息素浓度之和，由于计算选择概率
//        double test_tao=0.0;
        double load_proportion=last_load+1;          //乘在距离上的剩余算力系数
        if(last_load<=0){
            last_load=0;
        }
        for(int i=0;i<node_num;i++){
//            test_tao=(Math.pow(tao[select_node][i],alpha)*
//                    Math.pow(1.0/distance[select_node][i],beta));
            if(visited[i]==0){           //只在还没走过的节点中选择，根据信息素浓度作为下一个

                sum+=(Math.pow(tao[select_node][i],alpha)*
                        Math.pow(1.0/distance[select_node][i]*load_proportion,beta));
            }
        }
        //计算每个节点被选中的概率

        for(int i=0;i<node_num;i++){
            if(visited[i]==1){
                possibility[i]=0.0;   //已经过节点，选中概率就是0
            }else {
                possibility[i]=(Math.pow(tao[select_node][i],alpha)*
                        Math.pow(1.0/(distance[select_node][i]*load_proportion),beta))/sum;
            }
        }

        double random_node=Math.random()*1.5;
        //为了增加随机性，不采用轮盘赌
//        double sum_possibility=0;
        int select=-1;         //初始化被选中的节点为-1
        //选择随机，直到n个概率加起来大于随机数，则选择该节点

//        while (sum_possibility<random_node){
//            for (int i=0;i<node_num;i++){
//                if(visited[i]==0){
//                    sum_possibility+=possibility[i];
//                    if(sum_possibility>=random_node){
//                        select=i;
//                        break;
//                    }
//                }
//            }
//            random_node=random.nextDouble();    //如果没跳出来再随机一次
//        }
        while (select==-1){
            for (int i=0;i<node_num;i++){
                if(possibility[i]>=random_node){
                    select=i;
                    if(last_load>0){
                        last_load-=load[i];
                    }else {
                        last_load=0;
                    }
                    break;
                }
            }
            random_node=Math.random()*1.5;    //如果没跳出来再随机一次
        }
//        if(select==-1)
//            System.out.println("Fa♂！！！");
        System.out.println("此时选中准备作为下一个的节点编号为：\n"+select);
        node_path.add(select);    //将选定的节点编号写入路径
        visited[select]=1;        //将所确定的下一个节点编号修改状态为已访问
        c_node=select;            //选定的下一节点作为下一次迭代的点
    }

    public int getCurrent_node() {
        return c_node;
    }

    //计算路径代价
    public void Cal_pathLength(int [][]distance){
//        path_length=0;
        node_path_length=0;
        for (int i=0;i<node_path.size()-1;i++){
            node_path_length+=distance[node_path.get(i)][node_path.get(i+1)];
        }

    }
    //初始化蚂蚁子，把他们全扔起点去
    public void RandomSelect(int node_count){
        node_num=node_count;
        last_load=1.0;    //初始需卸载的量为1

        visited=new int[node_num];

        node_path=new ArrayList<>();

        for (int i=0;i<node_num;i++){
            visited[i]=0;
        }//初始化
        int first_node=0;           //让蚂蚁全部从0号出发开始找
        visited[first_node]=1;
        node_path.add(first_node);
    }
}
