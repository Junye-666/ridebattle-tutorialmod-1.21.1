package com.jpigeon.tutorialmod.rider.skills;


import com.jpigeon.ridebattlelib.core.system.skill.SkillSystem;
import com.jpigeon.tutorialmod.TutorialMod;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class RiderSkills {
    // RiderSkill类仅限于声明骑士技能及注册其对应名称
    // 依旧ResourceLocation起手
    // 注册skillId
    public static final ResourceLocation RIDER_KICK = ResourceLocation.fromNamespaceAndPath(TutorialMod.MODID, "rider_kick");
    public static final ResourceLocation RIDER_PUNCH = ResourceLocation.fromNamespaceAndPath(TutorialMod.MODID, "rider_punch");

    // 一个static注册方法
    public static void registerSkills(){
        SkillSystem.registerSkillName(
                RIDER_KICK,
                Component.literal("骑！士！踢！").withStyle(ChatFormatting.RED) // 显示红色的字
        );
        SkillSystem.registerSkillName(
                RIDER_PUNCH,
                Component.literal("骑！士！拳！").withStyle(ChatFormatting.GOLD) // 显示金色的字
        );
    }

}
