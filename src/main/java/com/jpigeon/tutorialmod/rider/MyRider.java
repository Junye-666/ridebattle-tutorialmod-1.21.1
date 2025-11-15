package com.jpigeon.tutorialmod.rider;

import com.jpigeon.ridebattlelib.core.system.form.FormConfig;
import com.jpigeon.ridebattlelib.core.system.henshin.RiderConfig;
import com.jpigeon.ridebattlelib.core.system.henshin.RiderRegistry;
import com.jpigeon.ridebattlelib.core.system.henshin.helper.TriggerType;
import com.jpigeon.tutorialmod.TutorialMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Items;

import java.util.List;

public class MyRider {
    // 首先需要RiderConfig: 相当于骑士的身份证, 名字是啥? 用的啥驱动器? 等等等等
    // RiderId: 骑士的身边辨识, 也就是大名, ResourceLocation
    public static final ResourceLocation RIDER_ID = ResourceLocation.fromNamespaceAndPath(TutorialMod.MODID, "my_rider");
    public static final ResourceLocation SLOT_ONE = ResourceLocation.fromNamespaceAndPath(TutorialMod.MODID, "myrider_slot_one");

    // RiderConfig的实际组装
    public static final RiderConfig MYRIDER = new RiderConfig(RIDER_ID)
            .setDriverItem(Items.GOLDEN_LEGGINGS, // 金护腿为驱动器
                    EquipmentSlot.LEGS) // 穿戴在腿部
            .addDriverSlot( // 在驱动器内"开个洞"放物品
                    SLOT_ONE, // 槽位ID
                    List.of(Items.GOLD_INGOT, Items.BONE), // 此槽位可以放 金锭, 骨头
                    true,
                    true
            )
            ;
    // 这样就是一个骑士的基本"身份证"了, 有了名也有了驱动器, 驱动器里还能放东西

    // 然后我们需要FormConfig: 定义骑士的各个形态, 变了后啥盔甲? 用的啥武器? 数值如何? 等等等等
    // FormId: 形态标识
    public static final ResourceLocation GOLD_FORM = ResourceLocation.fromNamespaceAndPath(TutorialMod.MODID, "gold_form");
    public static final ResourceLocation BONE_FORM = ResourceLocation.fromNamespaceAndPath(TutorialMod.MODID, "bone_form");

    public static final FormConfig MYRIDER_GOLD_FORM = new FormConfig(GOLD_FORM)
            .setArmor( // 定义形态的整体盔甲
                    Items.GOLDEN_HELMET,
                    Items.GOLDEN_CHESTPLATE,
                    null, // 传入null, 因为 *不需要覆盖驱动器的位置*, 传入一个另外的腿甲就会取代现有驱动器, 不过也可以用这个玩点不一样的, 比如单程票形态
                    Items.GOLDEN_BOOTS
            )
            .setTriggerType(TriggerType.KEY)
            .addEffect(MobEffects.GLOWING, 114514, 0, true) // 添加一个效果
            .addEffect(MobEffects.JUMP, 114514, 5, true) // 再添加一个效果
            .addGrantedItem(Items.GOLDEN_AXE.getDefaultInstance()) // 添加基于的物品(武器, 道具等等)
            .addRequiredItem(
                    SLOT_ONE, // 第一个槽位中
                    Items.GOLD_INGOT // 有金锭
            ) // 指定形态需要物品, 当腰带里为这些物品时变成这个形态
            ;

    public static final FormConfig MYRIDER_BONE_FORM = new FormConfig(BONE_FORM)
            .setArmor(
                    Items.SKELETON_SKULL,
                    Items.AIR, // 除Legs以外必须要求传入null以外的物品，可以是Items.AIR
                    null,
                    Items.AIR

            )
            .setTriggerType(TriggerType.AUTO) // AUTO，在物品插入时自动变身
            .addEffect(MobEffects.HUNGER, 114514, 0, true)
            .addGrantedItem(Items.BOW.getDefaultInstance())
            .addRequiredItem(SLOT_ONE, Items.BONE);


    public static void registerMyRider() { // 注册+绑定方法, 在这将骑士与形态配置绑定
        MYRIDER.addForm(MYRIDER_GOLD_FORM)
                .addForm(MYRIDER_BONE_FORM); // 将金形态赋予MYRIDER

        MYRIDER.setBaseForm(MYRIDER_GOLD_FORM.getFormId()); // 将金形态设为基础形态(这一句可选)

        MYRIDER_GOLD_FORM.setShouldPause(true); // 变身此形态时进入缓冲阶段
        MYRIDER_BONE_FORM.setShouldPause(false); // 为了对比，骨头形态不缓冲
        RiderRegistry.registerRider(MYRIDER); // 通过RiderRegistry这个类注册骑士

    }

    public static void init(){
        registerMyRider();
    }
}
