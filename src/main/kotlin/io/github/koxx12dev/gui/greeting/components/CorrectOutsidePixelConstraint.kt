package io.github.koxx12dev.gui.greeting.components

import gg.essential.elementa.UIComponent
import gg.essential.elementa.constraints.ConstraintType
import gg.essential.elementa.constraints.XConstraint
import gg.essential.elementa.constraints.resolution.ConstraintVisitor

class CorrectOutsidePixelConstraint(
    private val value: Float
) : XConstraint {
    override var cachedValue = 0f
    override var recalculate = true
    override var constrainTo: UIComponent? = null

    override fun getXPositionImpl(component: UIComponent) = value - component.getWidth()

    override fun visitImpl(visitor: ConstraintVisitor, type: ConstraintType) {

    }

}