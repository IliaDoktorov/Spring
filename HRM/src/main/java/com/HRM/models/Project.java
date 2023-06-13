package com.HRM.models;

import java.util.Date;
import java.util.List;

public class Project {
    private int id;
    private String name;
    private Person manager;
    private Date startDate;
    private List<Unit> units;
}
