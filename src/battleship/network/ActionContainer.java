package battleship.network;

import battleship.AttackType;

import java.io.Serializable;

/**
 * Created by amin on 1/25/15.
 */
public class ActionContainer implements Serializable
{
    boolean isTeamA;
    int x;
    int y;
    boolean rightClick;
    AttackType attackType;

    public boolean isTeamA()
    {
        return isTeamA;
    }

    public void setTeamA(boolean teamA)
    {
        this.isTeamA = teamA;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public boolean isRightClick()
    {
        return rightClick;
    }

    public void setRightClick(boolean rightClick)
    {
        this.rightClick = rightClick;
    }

    public AttackType getAttackType()
    {
        return attackType;
    }

    public void setAttackType(AttackType attackType)
    {
        this.attackType = attackType;
    }
}
