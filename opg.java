import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.Reader;
import java.util.Scanner;

public class opg {
    static char[] stack=new char[1005];
    static char[] inputc;
    static int top=0,bottom=0;
    public static void print(){
        int i;
        for(i=bottom;i<=top;i++) System.out.print(stack[i]+" ");
        System.out.println();
        return;
    }
    public static void print2(char[] sc){
        int i;
        for(i=bottom;i<=top;i++) System.out.print(sc[i]+" ");
        System.out.println();
        return;
    }
    public static int check(char a,char b){
        if(a=='i'){
            if(b=='i' || b=='(') return 0;
            else return 1;
        }
        else if(a=='+'){
            if(b=='i' || b=='*' || b=='(') return -1;
            else return 1;
        }
        else if(a=='*'){
            if(b=='i' || b=='(') return -1;
            else return 1;
        }
        else if(a=='('){
            if(b==')') return 2;
            else if(b=='#') return 1;
            else return -1;
        }
        else if(a==')'){
            if(b=='i' || b=='(') return 0;
            else return 1;
        }
        else if(a=='#'){
            if(b=='#' || b==')') return 0;
            else return -1;
        }
        else if(a=='N'){
            if(b=='#') return 1;
            else return -1;
        }
        return 0;
    }
    public static int guiyue(){
        char[] tmpc=stack.clone();
        int i,j,tmp;
        int l1,r1,l2,r2,l3,r3,l,r,p1=0,p2=0,p3=0,q1=0,q2=0,q3=0,f1=0,f2=0,f3=0;
        char now=' ',last=' ';
        l1=r1=l2=r2=l3=r3=l=r=top;
        for(i=bottom;i<=top;i++){
            if(tmpc[i]=='N'){
                tmp=check(tmpc[i-1],tmpc[i+1]);
                if(tmp==1) tmpc[i]='>';
                else if(tmp==-1) tmpc[i]='<';
                else if(tmp==2) tmpc[i]='=';
            }
        }
        //print2(tmpc);
        for(i=bottom;i<=top;i++){
            if(tmpc[i]=='>' || tmpc[i]=='=' || tmpc[i]=='<') now=tmpc[i];
            else continue;
            if(now=='<'){
                l1=i;
                p1=1;
            }
            else if((now=='=' && last=='<') || (now=='=' && last=='=')) r1=i+1;
            else if((now=='>' && last=='=') || (now=='>' && last=='<')){
                q1=1;
                r1=i;
                break;
            }
            last=now;
        }
        now=' ';
        last=' ';
        for(i=bottom;i<=top;i++){
            if(tmpc[i]=='>' || tmpc[i]=='=' || tmpc[i]=='<'){
                now=tmpc[i];
            }
            else continue;
            if(now=='=' && last!='='){
                l2=i-1;
                r2=i+1;
                p2=1;
                q2=1;
            }
            else if(now=='=' && last=='=') r2=i+1;
            else if(now!='=' && last=='='){
                q2=1;
                break;
            }
            last=now;
        }
        now=' ';
        last=' ';
        for(i=bottom;i<=top;i++){
            if(tmpc[i]=='i'){
                l3=i;
                r3=i;
                p3=1;
                q3=1;
                break;
            }
        }
        now=' ';
        last=' ';
        //print();
        f1=p1*q1;
        f2=p2*q2;
        f3=p3*q3;
        //System.out.println(f1+","+f2+","+f3);
        if(f1==1 && l1<=l){
            l=l1;
            r=r1;
        }
        if(f2==1 && l2<=l){
            l=l2;
            r=r2;
        }
        if(f3==1 && l3<=l){
            l=l3;
            r=r3;
        }
        //System.out.println((l-bottom)+","+(r-bottom));
        if(l==r && stack[r]=='i'){
            stack[r]='N';
            return 2;
        }
        if(r-l==2){
            stack[r]='N';
            for(j=r-1;j>=bottom+2;j--) stack[j]=stack[j-2];
            bottom+=2;
            return 2;
        }
        if(r==l && stack[r]=='N') return 1;
        return 0;
    }
    public static void main(String args[]){
        int i,l=0,result,flag;
        stack[top]='#';
//        String p1="in.txt";
        String p2=args[0];
        File f=new File(p2);
        Reader r=null;
        try {
            Scanner sc=new Scanner(new FileInputStream(f));
            inputc=(sc.nextLine()+"#").toCharArray();
            l=inputc.length;
        }catch (Exception e){
            e.printStackTrace();
        }
        for(i=0;i<l;){
            char t=stack[top],now=inputc[i];
            if(now!='i' && now!='(' && now!=')' && now!='+' && now!='*' && now!='#'){
                System.out.println("E");
                return;
            }
            if(stack[top]=='N') t=stack[top-1];
            if(t=='#' && now=='#') break;
            result=check(t,now);
            //System.out.println(stack[top]+" "+now);
            if(result==0){
                System.out.println("E");
                return;
            }
            else if(result==1){
                flag=guiyue();
                //print();
                if(flag==0){
                    System.out.println("RE");
                    return;
                }
                else if(flag==1){
                    return;
                }
                else if(flag==2){
                    System.out.println("R");
                }
            }
            else if(result==-1){
                top++;
                stack[top]=now;
                System.out.println("I"+now);
                i++;
            }
            else if(result==2){
                top++;
                stack[top]=now;
                System.out.println("I"+now);
                if(stack[top-1]=='(' && stack[top]==')') top-=2;
                i++;
            }
        }
        if(top-bottom==1 && stack[top]=='N');
        else System.out.println("RE");
    }
}
