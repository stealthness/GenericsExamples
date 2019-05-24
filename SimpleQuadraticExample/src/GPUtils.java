public class GPUtils {


    static GPBiFunction add = new GPBiFunction(Double::sum,"add","+");
    static GPBiFunction subtract = new GPBiFunction((a, b)-> a-b,"subtract","-");
    static GPBiFunction multiply = new GPBiFunction((a, b)-> a*b, "multiply","*");
    static GPBiFunction protectedDivision = new GPBiFunction((a, b)-> (b==0)?1.0:a/b,"protectedDivision","/");
    static GPSingleFunction sin = new GPSingleFunction((a) -> Math.sin(a),"sin","sin");

}
