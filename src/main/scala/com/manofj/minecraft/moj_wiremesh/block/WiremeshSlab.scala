package com.manofj.minecraft.moj_wiremesh.block

import java.util.Random

import net.minecraft.block.BlockSlab
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.properties.IProperty
import net.minecraft.block.properties.PropertyEnum
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

import com.manofj.minecraft.moj_commons.util.ImplicitConversions.BooleanExtension

import com.manofj.minecraft.moj_wiremesh.init.WiremeshBlocks


object WiremeshSlab {

  final val VARIANT = PropertyEnum.create( "variant", classOf[ WiremeshSlabVariant ] )

  class Double extends WiremeshSlab { override def isDouble: Boolean = true }
  class Half extends WiremeshSlab { override def isDouble: Boolean = false }

}

abstract class WiremeshSlab
  extends BlockSlab( Material.IRON )
  with    WiremeshBlock
{

  {
    setDefaultState( {
      var blockState = getDefaultState
      if ( !isDouble )
        blockState = blockState.withProperty( BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM )
      blockState.withProperty( WiremeshSlab.VARIANT, WiremeshSlabVariant.DEFAULT )
    } )
    setSoundType( SoundType.METAL )
    useNeighborBrightness = true
    setLightOpacity( 0 )
    translucent = false
    setCreativeTab( CreativeTabs.DECORATIONS )
  }

  override def getVariantProperty: IProperty[ _ ] = WiremeshSlab.VARIANT

  override def getTypeForItem( stack: ItemStack ): Comparable[ _ ] = WiremeshSlabVariant.DEFAULT

  override def getUnlocalizedName( meta: Int ): String = super.getUnlocalizedName


  override def createBlockState(): BlockStateContainer =
    ( isDouble ?> new BlockStateContainer( this, WiremeshSlab.VARIANT )
               !> new BlockStateContainer( this, BlockSlab.HALF, WiremeshSlab.VARIANT ) )

  override def getMetaFromState( state: IBlockState ): Int =
    ( !isDouble && state.getValue( BlockSlab.HALF ) == BlockSlab.EnumBlockHalf.TOP ) ?> 8 !> 0

  override def getStateFromMeta( meta: Int ): IBlockState = {
    val blockState = getDefaultState.withProperty( WiremeshSlab.VARIANT, WiremeshSlabVariant.DEFAULT )
    ( isDouble ?> blockState
               !> blockState.withProperty( BlockSlab.HALF,
      ( ( meta & 8 ) == 0 ) ?> BlockSlab.EnumBlockHalf.BOTTOM
                            !> BlockSlab.EnumBlockHalf.TOP ) )
  }

  override def getItem( worldIn: World, pos: BlockPos, state: IBlockState ): ItemStack =
    new ItemStack( WiremeshBlocks.WIRE_MESH_HALF )

  override def getItemDropped( state: IBlockState, rand: Random, fortune: Int ): Item =
    Item.getItemFromBlock( WiremeshBlocks.WIRE_MESH_HALF )


  override def getBlockLayer: BlockRenderLayer = BlockRenderLayer.CUTOUT

  override def isFullCube( state: IBlockState ): Boolean = false

  override def isOpaqueCube( state: IBlockState ): Boolean = false

  override def doesSideBlockRendering( state: IBlockState,
                                       world: IBlockAccess,
                                       pos:   BlockPos,
                                       face: EnumFacing ): Boolean = false

  override def shouldSideBeRendered( blockState:  IBlockState,
                                     blockAccess: IBlockAccess,
                                     pos: BlockPos,
                                     side: EnumFacing ): Boolean =
  {
    val sideState = blockAccess.getBlockState( pos.offset( side ) )
    val sideBlock = sideState.getBlock

    if ( sideState != blockState ) true
    else if ( sideBlock == this )  false
    else
      super.shouldSideBeRendered( blockState, blockAccess, pos, side )
  }

}
