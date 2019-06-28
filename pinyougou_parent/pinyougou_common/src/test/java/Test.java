import com.pinyougou.common.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author laozhu
 * @version $Id: Test, v 0.1 2019/6/26 11:39  Exp$
 */
public class Test {
   /* @Autowired
    private IdWorker idWorker;*/

    public static void main(String[] args) {
        IdWorker idWorker = new IdWorker();
        long l = idWorker.nextId();
        System.out.println(l);
    }
}
