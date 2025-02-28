package ferrothorn.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import ferrothorn.FerrothornMod;
import ferrothorn.characters.Ferrothorn;
import ferrothorn.stances.Sandstorm;

import static ferrothorn.FerrothornMod.makeCardPath;

public class ShoreUp extends AbstractDynamicCard {

    public static final String ID = FerrothornMod.makeID(ShoreUp.class.getSimpleName());
    public static final String IMG = makeCardPath("ShoreUp.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.POWER;       //
    public static final CardColor COLOR = Ferrothorn.Enums.COLOR_FERROTHORN;

    private static final int COST = 1;

//    private static final int BLOCK = 3;
//    private static final int UPGRADE_PLUS_BLOCK = 1;

    public ShoreUp() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        /*if (p.stance.ID.equals(Sandstorm.STANCE_ID) && p.hasPower(DexterityPower.POWER_ID))
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, this.magicNumber + p.getPower(DexterityPower.POWER_ID).amount), this.magicNumber + p.getPower(DexterityPower.POWER_ID).amount));
        else*/
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, this.magicNumber), this.magicNumber));

    }

    @Override
    public void applyPowers() {
        this.magicNumber = this.baseMagicNumber;
        AbstractPlayer p = AbstractDungeon.player;
        if (p.stance.ID.equals(Sandstorm.STANCE_ID) && p.hasPower(DexterityPower.POWER_ID)) {
            this.magicNumber += p.getPower(DexterityPower.POWER_ID).amount;
            super.applyPowers();
            this.isMagicNumberModified = this.isMagicNumberModified || (baseMagicNumber != magicNumber);
        } else
            super.applyPowers();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            //upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeMagicNumber(1);
        }
    }
}
