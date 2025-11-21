package com.jpigeon.tutorialmod.event;

import com.jpigeon.ridebattlelib.api.RiderManager;
import com.jpigeon.ridebattlelib.core.system.event.*;
import com.jpigeon.ridebattlelib.core.system.henshin.RiderConfig;
import com.jpigeon.tutorialmod.TutorialMod;
import com.jpigeon.tutorialmod.rider.MyRider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

@EventBusSubscriber(modid = TutorialMod.MODID)
public class EventHandler {
    @SubscribeEvent
    public static void onHenshin(HenshinEvent.Pre event){
        if (event.getRiderId().equals(MyRider.RIDER_ID)){
            // 在此实现特定骑士的(待机)通用动画/特效/音效
            // 使用RiderManger中提供的预制音效方法
            RiderManager.playPublicSound(event.getPlayer(), SoundEvents.PLAYER_LEVELUP); // 变身骑士前播放升级音效
        }

        if (event.getFormId().equals(MyRider.GOLD_FORM)){
            //实现特定形态的(待机)动画/特效/音效
            RiderManager.playPublicSound(event.getPlayer(), SoundEvents.AMETHYST_BLOCK_BREAK); // 变身金形态前：播放紫晶块破碎
        }

        // 骨头形态无待机音
    }

    @SubscribeEvent
    public static void onFormSwitch(FormSwitchEvent.Pre event){
        // 形态切换时的事件，总体与HenshinEvent一致，可以直接复制粘贴变身逻辑，也可以相加调整
        ResourceLocation oldFormId = event.getOldFormId(); // 可获取切换前形态
        ResourceLocation newFormId = event.getNewFormId(); // 可获取切换后形态

        // 可以实现更多花样（比如特定形态相互切换有特别效果）
    }

    // 因为MyRider中，金形态被暂停，进入缓冲，我们需要在某处继续变身流程
    @SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent event){
        // 这里在跳跃时完成变身
        if (event.getEntity() instanceof Player player){
            // 在跳跃时完成变身
            RiderManager.completeHenshin(player);
        }
    }

    @SubscribeEvent
    public static void postHenshin(HenshinEvent.Post event){
        // 变身完成音效/特效等
        if (event.getFormId().equals(MyRider.BONE_FORM)){
            RiderManager.playPublicSound(event.getPlayer(), SoundEvents.SKELETON_DEATH);// 变身骨头后播放音效
        }

        if (event.getFormId().equals(MyRider.GOLD_FORM)){
            RiderManager.playPublicSound(event.getPlayer(), SoundEvents.AMETHYST_BLOCK_CHIME);// 变身金形态后播放音效
        }
    }

    @SubscribeEvent
    public static void onUnhenshin(UnhenshinEvent.Pre event){
        Player player = event.getPlayer();
        RiderConfig config = RiderManager.getActiveRiderConfig(player);
        if (config == null) return;
        ResourceLocation formId = RiderManager.getCurrentForm(player);
        if (formId == null) return;

        if (config.equals(MyRider.MYRIDER)){
            // 为特定骑士播放解除音效/特效
            RiderManager.playPublicSound(player, SoundEvents.PLAYER_DEATH);
        }

        if (formId.equals(MyRider.GOLD_FORM)){
            // 同理
            RiderManager.playPublicSound(player, SoundEvents.GLASS_BREAK);
        }
    }

    // 其它Event演示
//
//    @SubscribeEvent
//    public static void onInsert(ItemInsertionEvent.Pre event){
//        if (event.getStack().is(Items.BONE)){ // 这里注意对比方式，不要用equals
//            event.setStack(Items.GOLD_INGOT.getDefaultInstance()); // 每当尝试存入骨头时就会变成金锭！
//        }
//    }
//
//    @SubscribeEvent
//    public static void onExtract(SlotExtractionEvent.Pre event){
//        if (event.getExtractedStack().is(Items.GOLD_INGOT)){
//            event.setExtractedStack(Items.IRON_INGOT.getDefaultInstance()); // 金锭拔出时会变成铁锭
//        }
//    }

}
