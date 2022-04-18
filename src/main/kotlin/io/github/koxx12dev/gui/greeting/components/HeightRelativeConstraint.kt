package io.github.koxx12dev.gui.greeting.components

import gg.essential.elementa.UIComponent
import gg.essential.elementa.constraints.ConstraintType
import gg.essential.elementa.constraints.HeightConstraint
import gg.essential.elementa.constraints.WidthConstraint
import gg.essential.elementa.constraints.resolution.ConstraintVisitor
import gg.essential.elementa.state.BasicState
import gg.essential.elementa.state.State

class HeightRelativeConstraint(value: Float) : WidthConstraint, HeightConstraint {
    override var cachedValue = 0f
    override var recalculate = true
    override var constrainTo: UIComponent? = null

    private var valueState: State<Float> = BasicState(value)

    var value: Float
        get() = valueState.get()
        set(value) { valueState.set(value) }

    override fun getWidthImpl(component: UIComponent): Float {
        return (constrainTo ?: component.parent).getHeight() * valueState.get()
    }

    override fun getHeightImpl(component: UIComponent): Float {
        return (constrainTo ?: component.parent).getHeight() * valueState.get()
    }

    override fun visitImpl(visitor: ConstraintVisitor, type: ConstraintType) {

    }
}