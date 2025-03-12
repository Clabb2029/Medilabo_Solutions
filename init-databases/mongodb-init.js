dbMed = db.getSiblingDB("MedilaboSolutions")

dbMed.notes.insertMany([
    {
        patientId: 1,
        description: "<p>Le patient d&eacute;clare qu'il 'se sent tr&egrave;s bien' Poids &eacute;gal ou inf&eacute;rieur au poids recommand&eacute;</p>",
        date: ISODate("2025-03-08T23:00:00Z")
    },
    {
        patientId: 2,
        description: "<p>Le patient d&eacute;clare qu'il ressent beaucoup de stress au travail Il se plaint &eacute;galement que son audition est anormale derni&egrave;rement</p>",
        date: ISODate("2025-03-08T23:00:00Z")
    },
    {
        patientId: 2,
        description: "<p>Le patient d&eacute;clare avoir fait une r&eacute;action aux m&eacute;dicaments au cours des 3 derniers mois Il remarque &eacute;galement que son audition continue d'&ecirc;tre anormale</p>",
        date: ISODate("2025-03-08T23:00:00Z")
    },
    {
        patientId: 3,
        description: "<p>Le patient d&eacute;clare qu'il fume depuis peu</p>",
        date: ISODate("2025-03-08T23:00:00Z")
    },
    {
        patientId: 3,
        description: "<p>Le patient d&eacute;clare qu'il est fumeur et qu'il a cess&eacute; de fumer l'ann&eacute;e derni&egrave;re Il se plaint &eacute;galement de crises d&rsquo;apn&eacute;e respiratoire anormales Tests de laboratoire indiquant un taux de cholest&eacute;rol LDL &eacute;lev&eacute;</p>",
        date: ISODate("2025-03-08T23:00:00Z")
    },
    {
        patientId: 4,
        description: "<p>Le patient d&eacute;clare qu'il lui est devenu difficile de monter les escaliers Il se plaint &eacute;galement d&rsquo;&ecirc;tre essouffl&eacute; Tests de laboratoire indiquant que les anticorps sont &eacute;lev&eacute;s R&eacute;action aux m&eacute;dicaments</p>",
        date: ISODate("2025-03-08T23:00:00Z")
    },
    {
        patientId: 4,
        description: "<p>Le patient d&eacute;clare qu'il a mal au dos lorsqu'il reste assis pendant longtemps</p>",
        date: ISODate("2025-03-08T23:00:00Z")
    },
    {
        patientId: 4,
        description: "<p>Le patient d&eacute;clare avoir commenc&eacute; &agrave; fumer depuis peu H&eacute;moglobine A1C sup&eacute;rieure au niveau recommand&eacute;</p>",
        date: ISODate("2025-03-08T23:00:00Z")
    },
    {
        patientId: 4,
        description: "<p>Taille, Poids, Cholest&eacute;rol, Vertige et R&eacute;action</p>",
        date: ISODate("2025-03-08T23:00:00Z")
    }
])