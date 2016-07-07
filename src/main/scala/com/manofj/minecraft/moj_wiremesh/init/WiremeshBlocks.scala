package com.manofj.minecraft.moj_wiremesh.init

import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.init.Blocks
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemSlab
import net.minecraft.item.ItemStack

import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.common.registry.GameRegistry

import com.manofj.minecraft.moj_commons.util.ImplicitConversions.AnyExtension

import com.manofj.minecraft.moj_wiremesh.Wiremesh
import com.manofj.minecraft.moj_wiremesh.block.WiremeshSlab
import com.manofj.minecraft.moj_wiremesh.block.WiremeshTile


object WiremeshBlocks {

  final lazy val WIRE_MESH_DOUBLE = new WiremeshSlab.Double << { _.setHardness( 5.0F ).setResistance( 10.0F ).setUnlocalizedName( "moj_wiremesh.wiremesh_slab" ) }
  final lazy val WIRE_MESH_HALF   = new WiremeshSlab.Half   << { _.setHardness( 5.0F ).setResistance( 10.0F ).setUnlocalizedName( "moj_wiremesh.wiremesh_slab" ) }
  final lazy val WIRE_MESH_TILE   = new WiremeshTile        << { _.setHardness( 5.0F ).setResistance( 10.0F ).setUnlocalizedName( "moj_wiremesh.wiremesh_tile" ) }


  // ブロック、レシピの登録を行う
  private[ moj_wiremesh ] def preInit(): Unit = {
    WIRE_MESH_DOUBLE << { doubleSlab =>
    WIRE_MESH_HALF   << { halfSlab =>

      val resourceLocation = Wiremesh.resourceLocation( "wiremesh_slab" )
      GameRegistry.register( doubleSlab, Wiremesh.resourceLocation( "wiremesh_double_slab" ) )
      GameRegistry.register( halfSlab, resourceLocation )

      val itemSlab = new ItemSlab( halfSlab, halfSlab, doubleSlab ).setUnlocalizedName( "moj_wiremesh.wiremesh_slab" )
      GameRegistry.register( itemSlab, resourceLocation )

      val modelResourceLocation = new ModelResourceLocation( resourceLocation, "inventory" )
      ModelLoader.setCustomModelResourceLocation( itemSlab, 0, modelResourceLocation )

      GameRegistry.addRecipe( new ItemStack( halfSlab, 2 ), Seq(
        "###",
        "# #",
        "###",
        '#': Character, Blocks.IRON_BARS
      ): _* )

    } }

    WIRE_MESH_TILE << { tile =>

      val resourceLocation = Wiremesh.resourceLocation( "wiremesh_tile" )
      GameRegistry.register( tile, resourceLocation )

      val itemBlock = new ItemBlock( tile ).setUnlocalizedName( "moj_wiremesh.wiremesh_tile" )
      GameRegistry.register( itemBlock, resourceLocation )

      val modelResourceLocation = new ModelResourceLocation( resourceLocation, "inventory" )
      ModelLoader.setCustomModelResourceLocation( itemBlock, 0, modelResourceLocation )

      GameRegistry.addShapelessRecipe( new ItemStack( tile, 4 ), WIRE_MESH_HALF )

    }

  }

}
