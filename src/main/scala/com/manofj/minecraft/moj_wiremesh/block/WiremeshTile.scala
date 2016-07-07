package com.manofj.minecraft.moj_wiremesh.block

import net.minecraft.block.Block
import net.minecraft.block.BlockSlab
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

import com.manofj.minecraft.moj_commons.util.ImplicitConversions.BooleanExtension


object WiremeshTile {

  private[ WiremeshTile ] final val AABB_TILE_BOTTOM = new AxisAlignedBB( 0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D )
  private[ WiremeshTile ] final val AABB_TILE_TOP    = new AxisAlignedBB( 0.0D, 0.9375D, 0.0D, 1.0D, 1.0D, 1.0D )

}

class WiremeshTile
  extends Block( Material.IRON )
  with    WiremeshBlock
{

  {
    setDefaultState( getDefaultState.withProperty( BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM ) )
    setSoundType( SoundType.METAL )
    useNeighborBrightness = true
    setLightOpacity( 0 )
    translucent = false
    setCreativeTab( CreativeTabs.DECORATIONS )
  }

  override def getBoundingBox( state: IBlockState, source: IBlockAccess, pos: BlockPos ): AxisAlignedBB =
    ( ( state.getValue( BlockSlab.HALF ) == BlockSlab.EnumBlockHalf.BOTTOM ) ?> WiremeshTile.AABB_TILE_BOTTOM
                                                                             !> WiremeshTile.AABB_TILE_TOP )

  override def createBlockState(): BlockStateContainer = new BlockStateContainer( this, BlockSlab.HALF )

  override def getMetaFromState( state: IBlockState ): Int =
    ( state.getValue( BlockSlab.HALF ) == BlockSlab.EnumBlockHalf.TOP ) ?> 8 !> 0

  override def getStateFromMeta( meta: Int ): IBlockState =
    getDefaultState.withProperty( BlockSlab.HALF,
      ( ( meta & 8 ) == 0 ) ?> BlockSlab.EnumBlockHalf.BOTTOM
                            !> BlockSlab.EnumBlockHalf.TOP )

  override def onBlockPlaced( worldIn: World,
                              pos:     BlockPos,
                              facing:  EnumFacing,
                              hitX: Float,
                              hitY: Float,
                              hitZ: Float,
                              meta: Int,
                              placer: EntityLivingBase )
  : IBlockState = {
    val blockState = super.onBlockPlaced( worldIn, pos, facing, hitX, hitY, hitZ, meta, placer )
    ( ( facing != EnumFacing.DOWN && ( facing == EnumFacing.UP || hitY <= 0.5D ) )
      ?> blockState.withProperty( BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM )
      !> blockState.withProperty( BlockSlab.HALF, BlockSlab.EnumBlockHalf.TOP )
  ) }

  override def isFullCube( state: IBlockState ): Boolean = false

  override def isFullyOpaque( state: IBlockState ): Boolean = state.getValue( BlockSlab.HALF ) == BlockSlab.EnumBlockHalf.TOP

  override def isOpaqueCube( state: IBlockState ): Boolean = false

  override def getBlockLayer: BlockRenderLayer = BlockRenderLayer.CUTOUT

}
