package de.phl.programmingproject.candyproduction;

import de.phl.programmingproject.TestBase;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test class for {@link CandyProducer} exercise.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({CandyProducer.class, CandyProductionLineTest.class})
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
        }
        catch (AssertionError e){
            fail("The 'main' method of the 'CandyProducer' class does not create a 'Candy' object with a 'SugarMix' as argument.");
        }

        Assertions.assertTrue(sugarMix.getFlavors().contains("Strawberry") || sugarMix.getFlavors().contains("strawberry"), "The sugar mix of the candy does not contain 'Strawberry'.");
        Assertions.assertTrue(sugarMix.getFlavors().contains("Blueberry") || sugarMix.getFlavors().contains("blueberry"), "The sugar mix of the candy does not contain 'Blueberry'.");
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
    public void task_3_main_method_creates_candy_with_strawberry_and_blueberry_sugar_mix_flavor_and_lemon_juicy_core() throws Exception {

        PowerMockito.whenNew(Candy.class).withArguments(Mockito.any(SugarMix.class),
                Mockito.any(JuicyCore.class)).then(invocation -> {
            sugarMix = invocation.getArgument(0);
            juicyCore = invocation.getArgument(1);
            return candySpy;
        }).thenReturn(candySpy);

        CandyProducer.main(null);
        try {
            PowerMockito.verifyNew(Candy.class).withArguments(Mockito.any(SugarMix.class),
                    Mockito.any(JuicyCore.class));
        }
        catch (AssertionError e){
            fail("The 'main' method of the 'CandyProducer' class does not create a 'Candy' object with 'SugarMix' and 'JuicyCore' as arguments.");
        }
        Assertions.assertTrue(sugarMix.getFlavors().contains("Strawberry") || sugarMix.getFlavors().contains("strawberry"), "The sugar mix of the candy does not contain 'Strawberry'.");
        Assertions.assertTrue(sugarMix.getFlavors().contains("Blueberry") || sugarMix.getFlavors().contains("blueberry"), "The sugar mix of the candy does not contain 'Blueberry'.");
        Assertions.assertEquals("lemon", juicyCore.getFlavor().toLowerCase(), "The juicy core of the candy does not have the flavor 'Lemon'.");
    }

    @Test
    public void task_4_printCandy_prints_string_representation() throws NoSuchMethodException {
        String filePath = "./src/main/java/de/phl/programmingproject/candyproduction/CandyProducer.java";
        String candyProducerFileContent = null;
        try {
            candyProducerFileContent = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertTrue(candyProducerFileContent.contains("String.format"),
                "The 'printCandy' method of the 'CandyProducer' class does not use the 'String.format' method to create the string representation of the candy.");

    }

    @Test
    public void task_5_produceCandies_produces_amount_unique_candies() {
        CandyFactory candyFactory = new CandyFactory();
        candyFactory.addSugarMixFlavors(Arrays.asList("Strawberry", "Blueberry", "Vanilla", "Chocolate", "Caramel"));
        candyFactory.addJuicyCoreFlavors(Arrays.asList("Lemon", "Cherry", "Raspberry"));

        Set<Candy> candies = candyFactory.produceCandies(45);
        assertNotNull(candies, "The 'produceCandies' method of the 'CandyFactory' class does not return a set of candies.");

        // verify that all produced candies are unique
        for (Candy candy : candies) {
            for (Candy candy1 : candies) {
                if (candy == candy1) continue;
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
}
