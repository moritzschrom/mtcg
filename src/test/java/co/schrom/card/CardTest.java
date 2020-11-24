package co.schrom.card;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardTest {

    @Mock
    CardInterface cardMock;

    @Test
    @DisplayName("Monster cards should be of CardType.MONSTER")
    void testGetCardType__monster() {
        // arrange
        Card monsterCard = new MonsterCard();

        // act
        CardType cardType = monsterCard.getCardType();

        // assert
        assertNotNull(cardType);
        assertEquals(CardType.MONSTER, cardType);
    }

    @Test
    @DisplayName("Spell cards should be of CardType.SPELL")
    void testGetCardType__spell() {
        // arrange
        Card spellCard = new SpellCard();

        // act
        CardType cardType = spellCard.getCardType();

        // assert
        assertNotNull(cardType);
        assertEquals(CardType.SPELL, cardType);
    }

    @Test
    @DisplayName("Dragons are immune against Goblins")
    void testWinsAgainst__dragonGoblin() {
        // arrange
        Card dragon = MonsterCard.builder().name("Dragon").build();
        when(cardMock.getCardType()).thenReturn(CardType.MONSTER);
        when(cardMock.getName()).thenReturn("Goblin");

        // act
        boolean result = dragon.winsAgainst(cardMock);

        // assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Wizards can control Orks so they are not able to damage them")
    void testWinsAgainst__wizardOrk() {
        // arrange
        Card wizard = MonsterCard.builder().name("Wizard").build();
        when(cardMock.getCardType()).thenReturn(CardType.MONSTER);
        when(cardMock.getName()).thenReturn("Ork");

        // act
        boolean result = wizard.winsAgainst(cardMock);

        // assert
        assertTrue(result);
    }

    @Test
    @DisplayName("The armor of Knights is so heavy that WaterSpells make them drown them instantly")
    void testWinsAgainst__waterSpellKnight() {
        // arrange
        Card waterSpell = SpellCard.builder().elementType(ElementType.WATER).build();
        when(cardMock.getCardType()).thenReturn(CardType.MONSTER);
        when(cardMock.getName()).thenReturn("Knight");

        // act
        boolean result = waterSpell.winsAgainst(cardMock);

        // assert
        assertTrue(result);
    }

    @Test
    @DisplayName("The Kraken is immune against spells")
    void testWinsAgainst__krakenSpell() {
        // arrange
        Card kraken = MonsterCard.builder().name("Kraken").build();
        when(cardMock.getCardType()).thenReturn(CardType.SPELL);

        // act
        boolean result = kraken.winsAgainst(cardMock);

        // assert
        assertTrue(result);
    }

    @Test
    @DisplayName("The FireElves know Dragons since they were little and can evade their attacks")
    void testWinsAgainst__fireElveDragon() {
        // arrange
        Card fireElve = MonsterCard.builder().name("FireElve").build();
        when(cardMock.getCardType()).thenReturn(CardType.MONSTER);
        when(cardMock.getName()).thenReturn("Dragon");

        // act
        boolean result = fireElve.winsAgainst(cardMock);

        // assert
        assertTrue(result);
    }
}
