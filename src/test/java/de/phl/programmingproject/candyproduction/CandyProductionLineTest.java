package de.phl.programmingproject.candyproduction;

import de.phl.programmingproject.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CandyProductionLineTest {
    private static boolean matches(String value, String... allowed) {
        return value != null && Arrays.stream(allowed).anyMatch(value::equalsIgnoreCase);
    }

    private static boolean berryMix(Object value) {
        if (!(value instanceof SugarMix mix)) return false;
        return mix.getFlavors().stream().anyMatch(s -> matches(s, "Strawberry", "Erdbeere"))
                && mix.getFlavors().stream().anyMatch(s -> matches(s, "Blueberry", "Heidelbeere", "Blaubeere"));
    }

    // Rückgaben bleiben konsistent, falls main die erzeugten Bonbons ausgibt.
    private List<List<?>> constructionArguments() {
        List<List<?>> calls = new ArrayList<>();
        try (MockedConstruction<Candy> construction = mockConstruction(Candy.class, (mock, context) -> {
            List<?> args = new ArrayList<>(context.arguments());
            calls.add(args);
            when(mock.getSugarMix()).thenReturn((SugarMix) args.getFirst());
            when(mock.hasJuicyCore()).thenReturn(args.size() == 2 && args.get(1) != null);
            if (args.size() == 2) when(mock.getJuicyCore()).thenReturn((JuicyCore) args.get(1));
        })) {
            CandyProducer.main(new String[0]);
        }
        return calls;
    }

    @Test
    void task_1_main_creates_berry_candy() {
        assertTrue(constructionArguments().stream().anyMatch(args -> args.size() == 1 && berryMix(args.getFirst())),
                "Erzeugen Sie ein Bonbon ohne Kern mit Erdbeere/Strawberry und Heidelbeere/Blaubeere/Blueberry.");
    }

    @Test
    void task_2_constructor_assigns_sugar_mix() {
        SugarMix mix = new SugarMix(Set.of("Erdbeere", "Blaubeere"));
        assertSame(mix, new Candy(mix, new JuicyCore("Zitrone")).getSugarMix(), "Die Zuckermischung muss übernommen werden.");
    }

    @Test
    void task_2_getJuicyCore_returns_core() {
        JuicyCore core = new JuicyCore("Zitrone");
        assertSame(core, new Candy(new SugarMix(Set.of("Erdbeere")), core).getJuicyCore());
        assertThrows(NoSuchElementException.class, () -> new Candy(new SugarMix(Set.of("Erdbeere"))).getJuicyCore());
    }

    @Test
    void task_2_constructor_rejects_null() {
        assertThrows(IllegalArgumentException.class, () -> new Candy(null, new JuicyCore("Zitrone")));
        assertThrows(IllegalArgumentException.class, () -> new Candy(new SugarMix(Set.of("Erdbeere")), null));
    }

    @Test
    void task_3_main_creates_berry_candy_with_lemon_core() {
        assertTrue(constructionArguments().stream().anyMatch(args -> args.size() == 2 && berryMix(args.getFirst())
                        && args.get(1) instanceof JuicyCore core && matches(core.getFlavor(), "Lemon", "Zitrone")),
                "Dasselbe Bonbon muss beide Beerenaromen und einen Kern mit Zitrone/Lemon enthalten.");
    }

    @Test
    void task_4_printCandy_uses_String_format() {
        String source = TestUtils.getFileContentForFileInRootOrSrcDirectory("/main/java/de/phl/programmingproject/candyproduction/CandyProducer.java");
        assertTrue(source.contains("String.format"), "Verwenden Sie String.format für die Bonbonausgabe.");
    }

    private CandyFactory preparedFactory() {
        CandyFactory factory = new CandyFactory();
        factory.addSugarMixFlavors(List.of("Erdbeere", "Blaubeere", "Vanille"));
        factory.addJuicyCoreFlavors(List.of("Zitrone", "Kirsche"));
        return factory;
    }

    @Test
    void task_5_produces_requested_amount_of_separate_candies() {
        for (int amount : new int[]{1, 3, 12}) {
            List<Candy> candies = preparedFactory().produceCandies(amount);
            assertNotNull(candies, "Geben Sie eine Liste zurück.");
            assertEquals(amount, candies.size(), "Die Anzahl muss amount entsprechen.");
            Set<Candy> identities = Collections.newSetFromMap(new IdentityHashMap<>());
            for (Candy candy : candies) {
                assertNotNull(candy);
                assertTrue(identities.add(candy), "Erzeugen Sie für jeden Listeneintrag ein neues Bonbon.");
                assertEquals(Set.of("Erdbeere", "Blaubeere"), candy.getSugarMix().getFlavors());
                assertTrue(candy.hasJuicyCore(), "Jedes Bonbon benötigt einen Kern.");
                assertEquals("Zitrone", candy.getJuicyCore().getFlavor());
            }
        }
    }

    @Test
    void task_5_rejects_nonpositive_amount() {
        assertThrows(IllegalArgumentException.class, () -> preparedFactory().produceCandies(0));
        assertThrows(IllegalArgumentException.class, () -> preparedFactory().produceCandies(-1));
    }

    @Test
    void task_5_rejects_missing_ingredients() {
        CandyFactory factory = new CandyFactory();
        assertThrows(IllegalStateException.class, () -> factory.produceCandies(1));
        factory.addJuicyCoreFlavors(List.of("Zitrone"));
        factory.addSugarMixFlavors(List.of("Erdbeere"));
        assertThrows(IllegalStateException.class, () -> factory.produceCandies(1));
        CandyFactory noCore = new CandyFactory();
        noCore.addSugarMixFlavors(List.of("Erdbeere", "Blaubeere"));
        assertThrows(IllegalStateException.class, () -> noCore.produceCandies(1));
    }
}
