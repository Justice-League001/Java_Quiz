import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Snow{}
class Powder extends Snow{}
class Light extends Powder{}
class Heavy extends Powder{}
class Crusty extends Snow{}
class Slush extends Crusty{}

public class AsListInference {

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		List<Snow>snow1=
				Arrays.asList(new Crusty(),new Slush(),new Powder());
		List<Snow>snow3=new ArrayList<Snow>();
		List<Snow>snow4=Arrays.<Snow>asList(
				new Light(),new Heavy());
	}

}
