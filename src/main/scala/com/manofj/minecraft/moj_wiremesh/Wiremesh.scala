package com.manofj.minecraft.moj_wiremesh

import net.minecraft.util.ResourceLocation

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

import com.manofj.minecraft.moj_commons.util.MinecraftMod

import com.manofj.minecraft.moj_wiremesh.init.WiremeshBlocks


@Mod( modid       = Wiremesh.modId,
      name        = Wiremesh.modName,
      version     = Wiremesh.modVersion,
      modLanguage = "scala")
object Wiremesh
  extends MinecraftMod
{

  final val modId      = "@modid@"
  final val modName    = "Wiremesh"
  final val modVersion = "@version@"


  // Wiremeshのリソースロケーションを生成する
  def resourceLocation( name: String ): ResourceLocation =
    new ResourceLocation( "moj_wiremesh:" + name )


  @Mod.EventHandler
  def preInit( event: FMLPreInitializationEvent ): Unit = {
    WiremeshBlocks.preInit()
  }

}
