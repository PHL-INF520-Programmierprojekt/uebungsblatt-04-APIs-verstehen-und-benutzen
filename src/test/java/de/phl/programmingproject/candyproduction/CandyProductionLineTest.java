package de.phl.programmingproject.candyproduction;

import de.phl.programmingproject.TestBase;
import de.phl.programmingproject.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test class for {@link CandyProducer} exercise.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ CandyProducer.class, CandyProductionLineTest.class })
public class CandyProductionLineTest extends TestBase {

    Candy candySpy;

    SugarMix strawberryAndBlueberrySugarMix = new SugarMix(new HashSet<>(Arrays.asList("Strawberry", "Blueberry")));
    SugarMix sugarMix;
    JuicyCore juicyCore;

    @Before
    public void initMocks() {
        candySpy = Mockito.spy(new Candy(strawberryAndBlueberrySugarMix, new JuicyCore("Lemon")));
    }

    @Test
    public void task_1_main_method_creates_candy_with_strawberry_and_blueberry_sugar_mix_flavor() throws Exception {

        PowerMockito.whenNew(Candy.class).withAnyArguments().then(invocation -> {
            sugarMix = invocation.getArgument(0);
            return candySpy;
        });

        CandyProducer.main(null);

        try {
            PowerMockito.verifyNew(Candy.class).withArguments(Mockito.any(SugarMix.class));
        } catch (AssertionError e) {
            fail("The 'main' method of the 'CandyProducer' class does not create a 'Candy' object with a 'SugarMix' as argument.");
        }

        Assertions.assertTrue(
                containsIgnoreCase(sugarMix.getFlavors(), "Strawberry"),
                "The sugar mix of the candy does not contain 'Strawberry'.");
        Assertions.assertTrue(
                containsIgnoreCase(sugarMix.getFlavors(),"Blueberry"),
                "The sugar mix of the candy does not contain 'Blueberry'.");
    }

    @Test
    public void task_2_constructor_with_sugar_mix_and_juicy_core_implemented() {
        Candy candy = new Candy(strawberryAndBlueberrySugarMix, new JuicyCore("Lemon"));
        assertNotNull(candy.getSugarMix(),
                "The constructor of the 'Candy' class with 'SugarMix' and 'JuicyCore' as arguments is not implemented correctly. The 'sugarMix' is not assigned.");
    }

    @Test
    public void task_2_getJuicyCore_returns_juicyCore() {
        Candy candy = new Candy(strawberryAndBlueberrySugarMix, new JuicyCore("Lemon"));
        assertNotNull(candy.getJuicyCore(),
                "The 'getJuicyCore' method of the 'Candy' class does not return the 'juicyCore'!.");
    }

    @Test
    public void task_3_main_method_creates_at_least_one_candy_with_strawberry_blueberry_mix_and_lemon_juicy_core()
            throws Exception {
        // collect ALL calls with (SugarMix, JuicyCore), then check if one matches
        List<SugarMix> capturedSugarMixes = new ArrayList<>();
        List<JuicyCore> capturedJuicyCores = new ArrayList<>();

        // Stub: 2-Arguments-Constructor -> Record arguments
        PowerMockito.whenNew(Candy.class)
                .withArguments(Mockito.any(SugarMix.class), Mockito.any(JuicyCore.class))
                .thenAnswer(invocation -> {
                    SugarMix sm = invocation.getArgument(0);
                    JuicyCore jc = invocation.getArgument(1);
                    capturedSugarMixes.add(sm);
                    capturedJuicyCores.add(jc);
                    return candySpy;
                });

        // Stub: 1-argument constructor -> doesn't matter, just no real build
        // if main() calls new Candy(new SugarMix(...)) with just one argument, we don’t care about its internals for this particular test. We only care about the two-argument constructor (Candy(SugarMix, JuicyCore)), because that’s the one Task 3 is testing.
        PowerMockito.whenNew(Candy.class)
                .withArguments(Mockito.any(SugarMix.class))
                .thenReturn(candySpy);

        CandyProducer.main(null);

        // Verify: At least one call with (SugarMix, JuicyCore)
        try {
            PowerMockito.verifyNew(Candy.class, Mockito.atLeastOnce())
                    .withArguments(Mockito.any(SugarMix.class), Mockito.any(JuicyCore.class));
        } catch (AssertionError e) {
            fail("The 'main' method of the 'CandyProducer' class never creates a 'Candy' with 'SugarMix' and 'JuicyCore'.");
        }

        // Check contents: Is there a match AMONG THE CAPTURED calls?
        boolean found = false;
        for (int i = 0; i < capturedSugarMixes.size(); i++) {
            SugarMix sm = capturedSugarMixes.get(i);
            JuicyCore jc = capturedJuicyCores.get(i);

            boolean hasStrawberry = containsIgnoreCase(sm.getFlavors(), "Strawberry");
            boolean hasBlueberry = containsIgnoreCase(sm.getFlavors(), "Blueberry");
            boolean isLemon = "lemon".equalsIgnoreCase(jc.getFlavor());

            if (hasStrawberry && hasBlueberry && isLemon) {
                found = true;
                break;
            }
        }

        assertTrue(found,
                "No Candy constructed with SugarMix containing 'Strawberry' and 'Blueberry' and JuicyCore flavor 'Lemon'.");
    }

    @Test
    public void task_4_printCandy_prints_string_representation() throws NoSuchMethodException {
        String candyProducerFileContent = TestUtils.getFileContentForFileInRootOrSrcDirectory("/main/java/de/phl/programmingproject/candyproduction/CandyProducer.java");

        Assertions.assertTrue(candyProducerFileContent.contains("String.format"),
                "The 'printCandy' method of the 'CandyProducer' class does not use the 'String.format' method to create the string representation of the candy.");
    }

    @Test
    public void task_5_produceCandies_produces_amount_unique_candies() {
        CandyFactory candyFactory = new CandyFactory();
        candyFactory.addSugarMixFlavors(Arrays.asList("Strawberry", "Blueberry", "Vanilla", "Chocolate", "Caramel"));
        candyFactory.addJuicyCoreFlavors(Arrays.asList("Lemon", "Cherry", "Raspberry"));

        Set<Candy> candies = candyFactory.produceCandies(45);
        assertNotNull(candies,
                "The 'produceCandies' method of the 'CandyFactory' class does not return a set of candies.");

        // verify that all produced candies are unique
        for (Candy candy : candies) {
            for (Candy candy1 : candies) {
                if (candy == candy1)
                    continue;
                if (candy.getSugarMix().getFlavors().equals(candy1.getSugarMix().getFlavors())) {
                    if (candy.hasJuiceCore() && candy1.hasJuiceCore()
                            && candy.getJuicyCore().equals(candy1.getJuicyCore())) {
                        fail("The 'produceCandies' method of the 'CandyFactory' class does not produce unique candies.");
                    } else if (!candy.hasJuiceCore() && !candy1.hasJuiceCore()) {
                        CandyProducer.printCandy(candy);
                        CandyProducer.printCandy(candy1);
                        fail("The 'produceCandies' method of the 'CandyFactory' class does not produce unique candies.");
                    }
                }
            }

        }
        Assertions.assertEquals(45, candies.size(),
                "The 'produceCandies' method of the 'CandyFactory' class does not produce the correct amount of candies.");

    }

    /**
     * Helper function to check if a collection contains a string, ignoring case.
     * @param values
     * @param target
     * @return
     */
    private static boolean containsIgnoreCase(Collection<String> values, String target) {
        for (String v : values) {
            if (v != null && v.equalsIgnoreCase(target))
                return true;
        }
        return false;
    }

}
