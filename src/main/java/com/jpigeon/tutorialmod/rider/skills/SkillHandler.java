package com.jpigeon.tutorialmod.rider.skills;

import com.jpigeon.ridebattlelib.core.system.event.SkillEvent;
import com.jpigeon.tutorialmod.TutorialMod;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = TutorialMod.MODID)
public class SkillHandler {
    // 技能主要是通过技能事件分发的
    @SubscribeEvent
    public static void preSkill(SkillEvent.Pre event){
        // 在这里可以简单实现你自己的技能冷却系统，因为没有内置
        if (event.getSkillId().equals(RiderSkills.RIDER_KICK)){
            //检查逻辑，比如通过一个LAST_USED_TIME Map
        }
    }

    @SubscribeEvent
    public static void onSkill(SkillEvent.Post event){
        // 在此可以通过对比skillId做出相应措施
        if (event.getSkillId().equals(RiderSkills.RIDER_KICK)){
            handleRiderKick(event.getPlayer());
        }
        if (event.getSkillId().equals(RiderSkills.RIDER_PUNCH)){
            handleRiderPunch(event.getPlayer());
        }

    }

    private static void handleRiderKick(Player player) {
        player.setDeltaMovement(0, 3, 0);
        // 任何奇妙效果都可以
    }

    private static void handleRiderPunch(Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 10*20)); // 赋予玩家10秒的力量效果(这里的duration是游戏刻)
    }
}
