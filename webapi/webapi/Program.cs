using Microsoft.AspNetCore.Http.HttpResults;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using webapi;

var builder = WebApplication.CreateBuilder(args);
builder.Services.AddOpenApi();
builder.Services.AddDbContext<ApplicationContext>(options =>
    options.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection")));
var app = builder.Build();

// 自动执行数据库迁移和种子数据
using (var scope = app.Services.CreateScope())
{
    var db = scope.ServiceProvider.GetRequiredService<ApplicationContext>();
    db.Database.Migrate(); // 应用所有待处理的迁移
    SeedData(db); // 添加种子数据
}

void SeedData(ApplicationContext db)
{
    if (!db.Items.Any())
    {
        db.Items.AddRange(
            new Item { Name = "Item 1" },
            new Item { Name = "Item 2" }
        );
        db.SaveChanges();
    }
}

if (app.Environment.IsDevelopment())
{
    app.MapOpenApi();
}
app.MapGet("/", async (ApplicationContext context) =>
{
    var items = await context.Items.ToListAsync();
    return Results.Ok(items);
});

app.MapPost("/createItem", async ([FromBody] Item item, ApplicationContext context) =>
{
    context.Items.Add(item);
    await context.SaveChangesAsync();
    return Results.Created($"/items/{item.Id}", item);
});
app.Run();