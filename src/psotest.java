import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class psotest {
    
    private final int iRang= 30;	////函数的取值范围
    static int dim =2;	//优化函数的维数
    static int sizepop =20;	//粒子数
    double sum1 = 0;
    double sum2 = 0;
    double g = 10000;	//全局最优解
    private Random random =new Random();	//返回一个随机值
    public double[][] pop =new double[sizepop][dim];	//粒子的位置
    public double[][] dpbest= new double[sizepop][dim];	//粒子记录的最优位置
    public double[][] V =new double[sizepop][dim];	//粒子的速度
    public double[] fitness= new double[sizepop];	//粒子的当前解
    public double[] gbest =new double[dim];	 	//全局最优解的位置
    public double[]fitnessgbest = new double[sizepop];	//全局最优解的坐标
    public double[]bestfitness = new double[sizepop];	//粒子本身的最优解
    int m_iTempPos =999;

    public void c() {
       for(int i = 0; i < sizepop;i++)	//对于每一个粒子的初始化
       {
           for(int j= 0; j < dim; j++) 
           {
              pop[i][j] = (random.nextDouble() - 0.5) * 2 *iRang; 	//初始化粒子的位置
              V[i][j] = dpbest[i][j] =pop[i][j];	//初始化粒子的速度
           }
           for(int j= 0; j < dim; j++) 
           {
              sum1 += Math.pow(pop[i][j],2);
              sum2 += Math.cos(2*Math.PI*pop[i][j]);
           }
           fitness[i]= -20 * Math.exp(-0.2 * Math.sqrt((1.0 / dim) * sum1))
                 - Math.exp((1.0 / dim) * sum2) + 20 +2.72;	//计算当前粒子的解
          bestfitness[i] = 10000;	//粒子本身的最优解赋值为一个大的值，目的是找最小值，
          if(bestfitness[i] > fitness[i]) 
          {
              bestfitness[i] =fitness[i];
              for(int j = 0; j< dim; j++) 
              {
                 dpbest[i][j] = pop[i][j];	//当前粒子本身的最优解为粒子的当前解，且记录的最优位置为粒子的当前位置
              }
           }
       }
       
       for(int i = 0; i < sizepop;i++)	//初始化完成之后，在所有的粒子中寻找最优解，
    	   								//则当前全局最优解为g，相应粒子的位置在gbest[j]中
       {
           if(bestfitness[i] < g) 
           {
                 g = bestfitness[i];
                 m_iTempPos = i;
           }
       }
       if (m_iTempPos != 999) 
       {
           for (int j= 0; j < dim; j++) 
           {
              gbest[j] = dpbest[m_iTempPos][j];
           }
       }
    }
    public static void main(String[] args) throws IOException {
       psotest pso = new psotest();
       pso.c();
       File file = new File("E:\\JavaProgram\\psotest\\input\\file.txt");
       BufferedWriter out=new BufferedWriter(new FileWriter(file,true));
       for(int i = 0; i < sizepop; i++)
       {
          out.write(i + " ");
           for(int j= 0; j < dim; j++) 
           {
              out.write(pso.pop[i][j] + " "+ pso.V[i][j] + " " + pso.dpbest[i][j] + " " + pso.gbest[j] + " ");
           }
          out.write(pso.bestfitness[i]+" "+pso.g);
          out.newLine();
          //文件中的每一行是一个粒子，其记录的内容分别是粒子的位置，粒子的速度，粒子本身的最优解的位置，全局最优解的位置以及值
          //总共20行，粒子的位置*2 + 粒子的速度*2 + 粒子本身的最优解的位置*2 + 粒子记录全局最优解的位置*2 + 全局最优解 + 全局最优解位置 总共10个数
          //它们的格式是：粒子位置1+粒子速度1+粒子本身的最优解的位置1+粒子记录全局最优解的位置1+
          				//粒子位置2+粒子速度2+粒子本身的最优解的位置2+粒子记录全局最优解的位置2+
          				//全局最优解 +全局最优解位置
       }
       out.close();
    }
}
