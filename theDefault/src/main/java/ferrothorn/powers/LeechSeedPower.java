package ferrothorn.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import ferrothorn.FerrothornMod;
import ferrothorn.util.TextureLoader;

import static ferrothorn.FerrothornMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class LeechSeedPower extends AbstractPower implements CloneablePowerInterface,HealthBarRenderPower {
    public AbstractCreature source;

    private static final Color barColor = Color.valueOf("44ee44");

    public static final String POWER_ID = FerrothornMod.makeID("LeechSeedPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("LeechSeed84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("LeechSeed32.png"));

    public LeechSeedPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = true  ;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
    }

    @Override
    public int getHealthBarAmount() {
        return amount;
    }

    @Override
    public Color getColor() {
        return barColor;
    }

    public void atEndOfTurn(boolean isPlayer) {
        int heal = this.amount / 2;
        if (this.owner.currentHealth < this.amount)
            heal = this.owner.currentHealth / 2;
       this.addToBot(new LoseHPAction(this.owner, AbstractDungeon.player, this.amount));
       this.addToBot(new HealAction(AbstractDungeon.player, this.owner, heal));
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new LeechSeedPower(owner, source, amount);
    }
}
