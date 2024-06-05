package com.example.csgo.utils.interfaces.weapon;

public enum Weapon {


    Flash(WeaponType.Grenade),
    UMP(WeaponType.SMG),
    Famas(WeaponType.Rifle),
    P2000(WeaponType.Pistol),
    Bizon(WeaponType.SMG),
    Swag7(WeaponType.Heavy),
    USP(WeaponType.Pistol),
    M4A1(WeaponType.Rifle),
    Glock(WeaponType.Pistol),
    Gallil(WeaponType.Rifle),
    CZ(WeaponType.Pistol),
    G3SG1(WeaponType.Sniper),
    Decoy(WeaponType.Grenade),
    SawedOff(WeaponType.Heavy),
    Negev(WeaponType.Heavy),
    Knife(WeaponType.Equipment),
    AWP(WeaponType.Sniper),
    HE(WeaponType.Grenade),
    Revolver(WeaponType.Pistol),
    M4A4(WeaponType.Rifle),
    AUG(WeaponType.Rifle),
    AK47(WeaponType.Rifle),
    Mac10(WeaponType.SMG),
    Scout(WeaponType.Sniper),
    Incendiary(WeaponType.Grenade),
    MP7(WeaponType.SMG),
    MP9(WeaponType.SMG),
    P250(WeaponType.Pistol),
    Molotov(WeaponType.Grenade),
    World (WeaponType.Unknown),
    FiveSeven(WeaponType.Pistol),
    Tec9(WeaponType.Pistol),
    XM1014(WeaponType.Heavy),
    DualBarettas(WeaponType.Pistol),
    Deagle(WeaponType.Pistol),
    Scar20(WeaponType.Sniper),
    SG556(WeaponType.Rifle),
    Nova(WeaponType.Heavy),
    Zeus(WeaponType.Equipment),
    P90(WeaponType.SMG),
    M249(WeaponType.Heavy),
    Smoke(WeaponType.Grenade);

    private final WeaponType weaponType;

    Weapon(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }
}