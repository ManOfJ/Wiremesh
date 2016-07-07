package com.manofj.minecraft.moj_wiremesh.block

import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.item.EntityItem
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

import com.manofj.minecraft.moj_commons.collection.java.alias.JavaList


// 金網ブロックをあらわす
trait WiremeshBlock
  extends Block
{

  // アイテムを通過させる処理
  override def addCollisionBoxToList( state:          IBlockState,
                                      worldIn:        World,
                                      pos:            BlockPos,
                                      entityBox:      AxisAlignedBB,
                                      collidingBoxes: JavaList[ AxisAlignedBB ],
                                      entityIn:       Entity ): Unit =
    entityIn match {
      case item: EntityItem =>
      case entity: Entity => super.addCollisionBoxToList( state, worldIn, pos, entityBox, collidingBoxes, entityIn )
      case _ =>
        if ( worldIn.getEntitiesWithinAABB( classOf[ EntityItem ], entityBox ).isEmpty )
          super.addCollisionBoxToList( state, worldIn, pos, entityBox, collidingBoxes, entityIn )
    }

}
