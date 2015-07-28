import de.foobar.mechanic.MathHelper;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import org.junit.Test;

public class MathHelperTest {


  @Test
  public void testMathHelperReturnsPrimeFactorsOfTwoIsJustOne() {

    List<Integer> numbers = new ArrayList<Integer>();
    numbers.add(2);
    numbers.add(1);
    List<Integer> result = MathHelper.getPrimeFactors(2, numbers);
    assertThat(result.size(), equalTo(1));
    assertThat(result, hasItem(1));
  }

  @Test
  public void testMathHelperReturnsEmptyByIfOneIsTheInList() {

    List<Integer> numbers = new ArrayList<Integer>();
    numbers.add(1);
    List<Integer> result = MathHelper.getPrimeFactors(1, numbers);
    assertThat(result.size(), equalTo(0));
  }


  @Test
  public void testMathHelperReturnsAllPrimeOf6() {

    int[] numbers = new int[]{1, 2, 3, 4, 5, 6};

    List<Integer> result = MathHelper.getPrimeFactors(6, MathHelper.convertIntArrayToList(numbers));
    assertThat(result.size(), equalTo(3));
    assertThat(result, hasItem(1));
    assertThat(result, hasItem(2));
    assertThat(result, hasItem(3));
    assertThat(result, not(hasItem(6)));

  }


  @Test
  public void testConvertIntArrayToList() {
    int[] numbers = new int[]{1, 2, 3, 4, 5, 6};
    List<Integer> list = MathHelper.convertIntArrayToList(numbers);
    assertThat(6, equalTo(list.size()));
  }



}
