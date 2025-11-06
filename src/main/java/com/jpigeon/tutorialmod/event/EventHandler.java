package com.jpigeon.tutorialmod.event;


import com.jpigeon.ridebattlelib.api.RiderManager;
import com.jpigeon.ridebattlelib.core.system.event.*;
import com.jpigeon.tutorialmod.TutorialMod;
import com.jpigeon.tutorialmod.rider.MyRider;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEvent;


@EventBusSubscriber(modid = TutorialMod.MODID)
public class EventHandler {
    @SubscribeEvent
    public static void preHenshin(HenshinEvent.Pre event){
        if (event.getRiderId().equals(MyRider.RIDER_ID)){
            // 仅在变身为MyRider中这个骑士时才执行后续逻辑
            Player player = event.getPlayer();
            // 调用RiderManager提供的快捷方式
            RiderManager.playPublicSound(
                    player,
                    SoundEvents.AMETHYST_BLOCK_BREAK,
                    SoundSource.PLAYERS,
                    1.0F, 1.0F);
        }
        // 也可以按照形态决定
        if (event.getFormId().equals(MyRider.GOLD_FORM)){
            RiderManager.playPublicSound(event.getPlayer(), SoundEvents.PLAYER_LEVELUP);
        }
        if (event.getFormId().equals(MyRider.BONE_FORM)){
            RiderManager.playPublicSound(event.getPlayer(), SoundEvents.SKELETON_STEP);
        }
    }

    // 因为现在变身被暂停，我们需要一个方法触发完成变身地动作
    // 比如在玩家跳跃时
    @SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent event){
        if (event.getEntity() instanceof Player player){
            RiderManager.completeHenshin(player); // 调用这个方法完成变身
        }
    }

    // 变身结束时音效
    @SubscribeEvent
    public static void postHenshin(HenshinEvent.Post event){
        if (event.getFormId().equals(MyRider.GOLD_FORM)){
            RiderManager.playPublicSound(event.getPlayer(), SoundEvents.PLAYER_LEVELUP);
        }
        if (event.getFormId().equals(MyRider.BONE_FORM)){
            RiderManager.playPublicSound(event.getPlayer(), SoundEvents.SKELETON_DEATH);
        }
    }

    // 其它事件
    // 解除变身事件
    @SubscribeEvent
    public static void onUnhenshin(UnhenshinEvent.Post event){
        // HenshinEvent同理
    }

    @SubscribeEvent
    public static void onItemInsert(ItemInsertionEvent event){
        if (event.getStack().is(Items.BONE)){
            event.setStack(Items.GOLD_INGOT.getDefaultInstance()); // 点骨成金
        }
    }

    @SubscribeEvent
    public static void onItemExtract(SlotExtractionEvent.Pre event){
        if (event.getExtractedStack().is(Items.GOLD_INGOT)){
            event.setExtractedStack(Items.IRON_INGOT.getDefaultInstance());
        }
    }
}
